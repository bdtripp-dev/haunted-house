document.addEventListener('DOMContentLoaded', async () => {
    const terminal = document.querySelector('#terminal');
    const hintLayer = document.querySelector('#hint-layer');
    const prompt = document.querySelector('#prompt');
    const inputContainer = document.querySelector('#input-container');
    const input = document.querySelector('#terminal-input');
    const cursor = document.querySelector('#cursor');
    const outputElement = document.querySelector('#output');
    const newGameBtn = document.querySelector('#new-game-btn');

    const state = {
        buffer: '',
        cursorPosition: 0,
        userMovedCaret: false,
        newGamePressed: false,
        gameQuit: false,
        isAndroid: /Android/i.test(navigator.userAgent),
        MAX_BUFFER: 30,
        STATUS: {
            RUNNING: 'RUNNING',
            STOPPED: 'STOPPED'
        }
    };
    
    function renderInput() {
        input.textContent = state.buffer || " ";
    }

    const startGame = async () => {
        const response = await fetch('api/game/start', {
            method: 'POST'
        });
        const data = await response.json();
        outputElement.textContent = data.output + '\n';
        renderInput();

        if (!state.newGamePressed) return;

        state.buffer = ' ';
        state.cursorPosition = 0;
        input.setAttribute("contenteditable", "true");
        prompt.style.display = 'initial';
        renderInput();
        focusCursor();
        state.newGamePressed = false;
        state.gameQuit = false;
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
        if (!state.isAndroid) return;

        const sel = window.getSelection();
        if (!sel.rangeCount) return;

        const range = sel.getRangeAt(0);

        // If Android IME reset caret to start (bug)
        const caretAtStart = range.startOffset === 0 && range.endOffset === 0;

        if (caretAtStart && !state.userMovedCaret) {
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
            (rect.left === 0 && rect.top === 0 && state.buffer.length > 0) ||
            rect.height === 0;

        if (rectIsInvalid) {
            const textNode = input.firstChild;
            if (textNode) {
                const fallbackRange = document.createRange();
                const pos = Math.min(state.cursorPosition, textNode.length);

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

        const pos = Math.min(state.cursorPosition, textNode.length);

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
        if (!state.gameQuit) {
            focusCursor();
        }
        hintLayer.classList.add('hidden');
    });

    // Required for iOS
    terminal.addEventListener('touchstart', () => {
        if (!state.gameQuit) {
            focusCursor();
        }
        hintLayer.classList.add('hidden');
    });

    input.addEventListener("input", () => {
        let text = input.textContent;

        if (text.length > state.MAX_BUFFER) {
            text = text.slice(0, state.MAX_BUFFER);
            input.textContent = text;
        }

        state.buffer = text;
        state.cursorPosition++;

        if (state.isAndroid) {
            setTimeout(() => {
                maybeFixAndroidCaret();
                updateCursor();
            }, 0);
        } else {
            updateCursor();
        }
    });

    input.addEventListener('keydown', async (e) => {
        if ((e.key === 'Backspace') && state.cursorPosition !== 0) {
            e.preventDefault();
            state.buffer = state.buffer.slice(0, state.cursorPosition - 1) + state.buffer.slice(state.cursorPosition);
            state.cursorPosition = Math.max(--state.cursorPosition, 0);
            state.userMovedCaret = false;
        } else if (e.key === "ArrowLeft") {
            e.preventDefault();
            state.cursorPosition = Math.max(--state.cursorPosition, 0);
            state.userMovedCaret = true;
        } else if ((e.key === "ArrowRight") && (state.cursorPosition < state.MAX_BUFFER)) {
            e.preventDefault();
            state.cursorPosition = Math.min(++state.cursorPosition, state.buffer.length);
            state.userMovedCaret = true;
        } else if (e.key === "Delete") {
            e.preventDefault();
            state.buffer = state.buffer.slice(0, state.cursorPosition) + state.buffer.slice(state.cursorPosition + 1);
            state.userMovedCaret = false;
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
            state.buffer = ' ';
            state.cursorPosition = 0;
            state.userMovedCaret = false;
            if (data.status === state.STATUS.STOPPED) {
                outputElement.textContent += 'Click "New Game" to play again!';
                input.setAttribute("contenteditable", "false");
                prompt.style.display = 'none';
                cursor.style.display = 'none';
                state.gameQuit = true;
            }
        }
         setTimeout(() => {
            renderInput();
            syncBrowserCaretToLogicalCursor();
            updateCursor();
        }, 0); 
        console.log("Buffer length: ", state.buffer.length); 
        console.log("Cursor position: ", state.cursorPosition);
    });

    newGameBtn.addEventListener('click', async () => {
        startGame();
        state.newGamePressed = true;
    });
});