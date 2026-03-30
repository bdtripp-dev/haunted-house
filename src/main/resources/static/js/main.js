document.addEventListener('DOMContentLoaded', async () => {
    const terminal = document.querySelector('#terminal');
    const prompt = document.querySelector('#prompt');
    const inputContainer = document.querySelector('#input-container');
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
    let newGamePressed = false;
    let gameQuit = false;

    function renderInput() {
        input.textContent = buffer || " ";
    }

    const startGame = async () => {
        const response = await fetch('api/game/start', {
            method: 'POST'
        });
        const data = await response.json();
        outputElement.textContent = data.output + '\n';
        renderInput();

        if (!newGamePressed) return;

        buffer = ' ';
        cursorPosition = 0;
        input.disabled = false;
        prompt.style.display = 'initial';
        renderInput();
        focusCursor();
        newGamePressed = false;
        gameQuit = false;
    }

    cursor.style.display = 'none';
    startGame();

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
        const containerRect = inputContainer.getBoundingClientRect();

        const rectIsInvalid =
            (rect.left === 0 && rect.top === 0 && buffer.length > 0) ||
            rect.height === 0;

        if (rectIsInvalid) {
            const textNode = input.firstChild;
            if (textNode) {
                const fallbackRange = document.createRange();
                const pos = Math.min(cursorPosition, textNode.length);

                fallbackRange.setStart(textNode, pos);
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

        const textNode = input.firstChild;
        if (!textNode) return;

        const pos = Math.min(cursorPosition, textNode.length);

        range.setStart(textNode, pos);
        range.collapse(true);

        sel.removeAllRanges();
        sel.addRange(range);
    }

    function focusCursor() {                    // position visual cursor
        input.focus();
        syncBrowserCaretToLogicalCursor();
        updateCursor();
        cursor.style.display = "initial";
    }

    terminal.addEventListener('click', () => {
        if (!gameQuit) {
            focusCursor();
        }
    });

    // Required for iOS
    terminal.addEventListener('touchstart', () => {
        if (!gameQuit) {
            focusCursor();
        }
    });

    input.addEventListener("input", () => {
        let text = input.textContent;

        if (text.length > MAX_BUFFER_LENGTH) {
            text = text.slice(0, MAX_BUFFER_LENGTH);
            input.textContent = text;
        }

        buffer = text;
        cursorPosition++;

        if (isAndroid) {
            setTimeout(() => {
                maybeFixAndroidCaret();
                updateCursor();
            }, 0);
        } else {
            updateCursor();
        }
    });

    input.addEventListener('keydown', async (e) => {
        if ((e.key === 'Backspace') && cursorPosition !== 0) {
            e.preventDefault();
            buffer = buffer.slice(0, cursorPosition - 1) + buffer.slice(cursorPosition);
            cursorPosition = Math.max(--cursorPosition, 0);
            userMovedCaret = false;
        } else if (e.key === "ArrowLeft") {
            e.preventDefault();
            cursorPosition = Math.max(--cursorPosition, 0);
            userMovedCaret = true;
        } else if ((e.key === "ArrowRight") && (cursorPosition < MAX_BUFFER_LENGTH)) {
            e.preventDefault();
            cursorPosition = Math.min(++cursorPosition, buffer.length);
            userMovedCaret = true;
        } else if (e.key === "Delete") {
            e.preventDefault();
            buffer = buffer.slice(0, cursorPosition) + buffer.slice(cursorPosition + 1);
            userMovedCaret = false;
        } else if (e.key === "Tab") {
            e.preventDefault();
            return; 
        }

        if (e.key === 'Enter') {
            e.preventDefault();
            const safeBuffer = e.target.textContent.trim();

            const response = await fetch('/api/game/command', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 'input': safeBuffer }),
            });

            if (!response.ok) {
                throw new Error(`Request failed: ${response.status}`);
            }

            const data = await response.json();

            outputElement.textContent += data.output;
            terminal.scrollTop = terminal.scrollHeight;
            buffer = ' ';
            cursorPosition = 0;
            userMovedCaret = false;
            if (data.status === STATUS.STOPPED) {
                outputElement.textContent += 'Click "New Game" to play again!';
                input.disabled = true;
                prompt.style.display = 'none';
                cursor.style.display = 'none';
                gameQuit = true;
            }
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
        newGamePressed = true;
    });
});