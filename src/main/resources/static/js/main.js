document.addEventListener('DOMContentLoaded', async () => {

    const dom = {
        terminal: document.querySelector('#terminal'),
        hintLayer: document.querySelector('#hint-layer'),
        prompt: document.querySelector('#prompt'),
        inputContainer: document.querySelector('#input-container'),
        input: document.querySelector('#terminal-input'),
        cursor: document.querySelector('#cursor'),
        output: document.querySelector('#output'),
        newGameBtn: document.querySelector('#new-game-btn')
    };

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

  // -----------------------------
  // Helper Functions
  // -----------------------------

  const DEBUG = window.LOCAL_DEBUG === true;

  function debug(...args) {
    if (DEBUG) console.log(...args);
  }

    // -----------------------------
    // Rendering
    // -----------------------------
    
    function renderInput() {
        dom.input.textContent = state.buffer || " ";
    }

    // -----------------------------
    // Game Lifecycle
    // -----------------------------

    const startGame = async () => {
        const response = await fetch('api/game/start', {
            method: 'POST'
        });
        const data = await response.json();
        dom.output.textContent = data.output + '\n';
        renderInput();

        if (!state.newGamePressed) return;

        state.buffer = ' ';
        state.cursorPosition = 0;
        dom.input.setAttribute("contenteditable", "true");
        dom.prompt.style.display = 'initial';
        renderInput();
        focusCursor();
        state.newGamePressed = false;
        state.gameQuit = false;
    }

    // -----------------------------
    // Caret + Cursor Positioning
    // -----------------------------

    function updateCursor() {
        const sel = window.getSelection();
        if (!sel.rangeCount) return;

        const range = sel.getRangeAt(0).cloneRange();
        const rect = range.getBoundingClientRect();
        const containerRect = dom.inputContainer.getBoundingClientRect();

        const rectIsInvalid =
            (rect.left === 0 && rect.top === 0 && state.buffer.length > 0) ||
            rect.height === 0;

        if (rectIsInvalid) {
            const textNode = dom.input.firstChild;
            if (textNode) {
                const fallbackRange = document.createRange();
                const pos = Math.min(state.cursorPosition, textNode.length);

                fallbackRange.setStart(textNode, pos);
                fallbackRange.collapse(true);

                const fallbackRect = fallbackRange.getBoundingClientRect();

                dom.cursor.style.left = `${fallbackRect.left - containerRect.left}px`;
                dom.cursor.style.top = `${fallbackRect.top - containerRect.top}px`;
                return;
            }
        }

        dom.cursor.style.left = `${rect.left - containerRect.left}px`;
        dom.cursor.style.top = `${rect.top - containerRect.top}px`;
    }

    function syncBrowserCaretToLogicalCursor() {
        const sel = window.getSelection();
        const range = document.createRange();

        const textNode = dom.input.firstChild;
        if (!textNode) return;

        const pos = Math.min(state.cursorPosition, textNode.length);

        range.setStart(textNode, pos);
        range.collapse(true);

        sel.removeAllRanges();
        sel.addRange(range);
    }

    const moveCaretToEnd = () => {
        const range = document.createRange();
        const sel = window.getSelection();

        range.selectNodeContents(dom.input);
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

    function focusCursor() {                    // position visual cursor
        dom.input.focus();
        syncBrowserCaretToLogicalCursor();
        updateCursor();
        dom.cursor.style.display = "initial";
    }

    // -----------------------------
    // Event Handlers
    // -----------------------------

    dom.terminal.addEventListener('click', () => {
        if (!state.gameQuit) {
            focusCursor();
        }
        dom.hintLayer.classList.add('hidden');
    });

    // Required for iOS
    dom.terminal.addEventListener('touchstart', () => {
        if (!state.gameQuit) {
            focusCursor();
        }
        dom.hintLayer.classList.add('hidden');
    });

    dom.input.addEventListener("input", () => {
        let text = dom.input.textContent;

        if (text.length > state.MAX_BUFFER) {
            text = text.slice(0, state.MAX_BUFFER);
            dom.input.textContent = text;
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

    dom.input.addEventListener('keydown', async (e) => {
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

            dom.output.textContent += data.output;
            dom.terminal.scrollTop = dom.terminal.scrollHeight;
            state.buffer = ' ';
            state.cursorPosition = 0;
            state.userMovedCaret = false;
            if (data.status === state.STATUS.STOPPED) {
                dom.output.textContent += 'Click "New Game" to play again!';
                dom.input.setAttribute("contenteditable", "false");
                dom.prompt.style.display = 'none';
                dom.cursor.style.display = 'none';
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

    dom.newGameBtn.addEventListener('click', async () => {
        startGame();
        state.newGamePressed = true;
    });

    // -----------------------------
    // Initialization
    // -----------------------------

    dom.cursor.style.display = 'none';
    startGame();
});