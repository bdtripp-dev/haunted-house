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

    // function renderInput() {
    //     input.innerText = '';

    //     Array.from(buffer).forEach((char, index) => {
    //         let span = document.createElement('span');
    //         span.textContent = char;
    //         if (index === cursorPosition) {
    //             span.className = 'cursor';
    //         }
    //         input.appendChild(span);
    //     });
    // }

    function renderInput() {
        input.textContent = buffer;
    }

    const startGame = async () => {
        input.disabled = false;
        // endCursor.classList.add('cursor');
        prompt.style.display = 'initial';
        const response = await fetch('api/game/start', {
            method: 'POST'
        });
        const data = await response.json();
        outputElement.textContent = data.output + '\n';
    }
    
    startGame();
    renderInput();

    const moveCursorToEnd = () => {
        const range = document.createRange();
        const sel = window.getSelection();

        range.selectNodeContents(input);
        range.collapse(false); // collapse to end
        sel.removeAllRanges();
        sel.addRange(range);
    };

    function updateCursor() {
        const sel = window.getSelection();
        if (!sel.rangeCount) return;

        const range = sel.getRangeAt(0).cloneRange();
        const rect = range.getBoundingClientRect();
        const containerRect = input.getBoundingClientRect();

        cursor.style.left = `${rect.left - containerRect.left}px`;
        cursor.style.top = `${rect.top - containerRect.top}px`;
    }

    terminal.addEventListener('click', () => {
        input.focus();
        moveCursorToEnd();
    });

    // Required for iOS
    terminal.addEventListener('touchstart', () => {
        input.focus();
        moveCursorToEnd();
    });

    input.addEventListener("focus", () => {
        moveCursorToEnd();
    });

    input.addEventListener('input', () => {
        if (/Android/i.test(navigator.userAgent)) {
            setTimeout(() => {
                moveCursorToEnd();
                updateCursor();
            }, 0);
        } else {
            updateCursor();
        }
    });

    input.addEventListener('keydown', async (e) => {
        e.preventDefault();

        if ((e.key === 'Backspace') && cursorPosition !== 0) {
            buffer = buffer.slice(0, cursorPosition - 1) + buffer.slice(cursorPosition);
            cursorPosition = Math.max(--cursorPosition, 0);
        } else if (e.key === "ArrowLeft") {
            cursorPosition = Math.max(--cursorPosition, 0);
        } else if ((e.key === "ArrowRight") && (cursorPosition < MAX_BUFFER_LENGTH)) {
            cursorPosition = Math.min(++cursorPosition, buffer.length);
        } else if (e.key === "Delete") {
            buffer = buffer.slice(0, cursorPosition) + buffer.slice(cursorPosition + 1);
        } else if (
            (e.key.length === 1) && 
            ((cursorPosition < MAX_BUFFER_LENGTH) && 
            (buffer.length < MAX_BUFFER_LENGTH))
        ) {
            buffer = buffer.slice(0, cursorPosition) + e.key + buffer.slice(cursorPosition);
            ++cursorPosition;
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
            if (data.status === STATUS.STOPPED) {
                outputElement.textContent += 'Click "New Game" to play again!';
                input.disabled = true;
                prompt.style.display = 'none';
                // endCursor.classList.remove('cursor');
            }
            return;
        }
        setTimeout(renderInput, 0);
        cursorPosition === buffer.length ? 
        // endCursor.className = 'cursor' : 
        // endCursor.classList.remove('cursor');
        console.log("Buffer length: ", buffer.length);
        console.log("Cursor position: ", cursorPosition);
    });

    newGameBtn.addEventListener('click', async () => {
        startGame();
    });
});