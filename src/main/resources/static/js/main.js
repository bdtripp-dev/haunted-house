document.addEventListener('DOMContentLoaded', async () => {
    const terminal = document.querySelector('#terminal');
    const prompt = document.querySelector('#prompt');
    const input = document.querySelector('#terminal-input');
    const cursor = document.querySelector('#cursor');
    const outputElement = document.querySelector('#output');
    const newGameBtn = document.querySelector('#new-game-btn');
    const STATUS = {
        RUNNING: 'RUNNING',
        STOPPED: 'STOPPED'
    };
    const MAX_BUFFER_LENGTH = 30;
    let buffer = '';
    let cursorPosition = 0;
    const isAndroid = /Android/i.test(navigator.userAgent);
    let userMovedCaret = false;

    function renderInput() {
        input.textContent = buffer;
    }

    const startGame = async () => {
        input.disabled = false;
        prompt.style.display = 'initial';
        const response = await fetch('api/game/start', {
            method: 'POST'
        });
        const data = await response.json();
        outputElement.textContent = data.output + '\n';
    }
    
    startGame();
    renderInput();

    const moveCaretToEnd = () => {
        const range = document.createRange();
        const sel = window.getSelection();

        range.selectNodeContents(input);
        range.collapse(false); // collapse to end
        sel.removeAllRanges();
        sel.addRange(range);
    };

    function maybeFixAndroidCaret() {
        if (!isAndroid) return;

        const sel = window.getSelection();
        if (!sel.rangeCount) return;

        const range = sel.getRangeAt(0);

        // If Android IME reset caret to start (bug)
        const caretAtStart = range.startOffset === 0 && range.endOffset === 0;

        if (caretAtStart && !userMovedCaret) {
            moveCaretToEnd();
        }
    }

    function updateCursor() {
        const sel = window.getSelection();
        if (!sel.rangeCount) return;

        const range = sel.getRangeAt(0).cloneRange();
        const rect = range.getBoundingClientRect();
        const containerRect = input.getBoundingClientRect();

        // If rect is invalid (collapsed at empty position)
        if (rect.left === 0 && rect.top === 0 && buffer.length > 0) {
            // fallback: compute position from logical cursor
            const textNode = input.firstChild;
            if (textNode) {
                const fallbackRange = document.createRange();
                fallbackRange.setStart(textNode, cursorPosition);
                fallbackRange.collapse(true);
                const fallbackRect = fallbackRange.getBoundingClientRect();

                cursor.style.left = `${fallbackRect.left - containerRect.left}px`;
                cursor.style.top = `${fallbackRect.top - containerRect.top}px`;
                return;
            }
        }

        cursor.style.left = `${rect.left - containerRect.left}px`;
        cursor.style.top = `${rect.top - containerRect.top}px`;
    }


    function syncBrowserCaretToLogicalCursor() {
        const sel = window.getSelection();
        const range = document.createRange();

        // Create a text node reference
        const textNode = input.firstChild;
        if (!textNode) return;

        const pos = Math.min(cursorPosition, textNode.length);

        range.setStart(textNode, pos);
        range.collapse(true);

        sel.removeAllRanges();
        sel.addRange(range);
    }

    terminal.addEventListener('click', () => {
        input.focus();
        updateCursor();
    });

    // Required for iOS
    terminal.addEventListener('touchstart', () => {
        input.focus();
        updateCursor();
    });

    input.addEventListener("focus", () => {
        updateCursor();
    });

    input.addEventListener("input", () => {
        if (isAndroid) {
            setTimeout(() => {
                maybeFixAndroidCaret(); // only fixes when Android breaks it
                updateCursor();         // always safe
            }, 0);
        } else {
            updateCursor();             // desktop/iOS
        }
    });

    input.addEventListener('keydown', async (e) => {
        e.preventDefault();

        if ((e.key === 'Backspace') && cursorPosition !== 0) {
            buffer = buffer.slice(0, cursorPosition - 1) + buffer.slice(cursorPosition);
            cursorPosition = Math.max(--cursorPosition, 0);
            userMovedCaret = false;
        } else if (e.key === "ArrowLeft") {
            cursorPosition = Math.max(--cursorPosition, 0);
            userMovedCaret = true;
        } else if ((e.key === "ArrowRight") && (cursorPosition < MAX_BUFFER_LENGTH)) {
            cursorPosition = Math.min(++cursorPosition, buffer.length);
            userMovedCaret = true;
        } else if (e.key === "Delete") {
            buffer = buffer.slice(0, cursorPosition) + buffer.slice(cursorPosition + 1);
            userMovedCaret = false;
        } else if (
            (e.key.length === 1) && 
            ((cursorPosition < MAX_BUFFER_LENGTH) && 
            (buffer.length < MAX_BUFFER_LENGTH))
        ) {
            buffer = buffer.slice(0, cursorPosition) + e.key + buffer.slice(cursorPosition);
            ++cursorPosition;
            userMovedCaret = false;
        }

        if (e.key === 'Enter') {
            const response = await fetch('/api/game/command', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 'input': e.target.textContent }),
            });

            if (!response.ok) {
                throw new Error(`Request failed: ${response.status}`);
            }

            const data = await response.json();

            outputElement.textContent += data.output;
            terminal.scrollTop = terminal.scrollHeight;
            input.innerText = '';
            buffer = '';
            cursorPosition = 0;
            userMovedCaret = false;
            if (data.status === STATUS.STOPPED) {
                outputElement.textContent += 'Click "New Game" to play again!';
                input.disabled = true;
                prompt.style.display = 'none';
                // endCursor.classList.remove('cursor');
            }
            return;
        }
         setTimeout(() => {
            renderInput();
            syncBrowserCaretToLogicalCursor();
            updateCursor();
        }, 0); 
        console.log("Buffer length: ", buffer.length); 
        console.log("Cursor position: ", cursorPosition);
    });

    newGameBtn.addEventListener('click', async () => {
        startGame();
    });
});