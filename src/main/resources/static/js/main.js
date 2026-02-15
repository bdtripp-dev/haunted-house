document.addEventListener('DOMContentLoaded', async () => {
    const terminal = document.querySelector('#terminal');
    const prompt = document.querySelector('#prompt');
    const input = document.querySelector('#terminal-input');
    const cursor = document.querySelector('#cursor');
    const endCursor = document.querySelector('#end-cursor');
    const outputElement = document.querySelector('#output');
    const newGameBtn = document.querySelector('#new-game-btn');
    const STATUS = {
        RUNNING: 'RUNNING',
        STOPPED: 'STOPPED'
    };

    let buffer = '';
    let cursorPosition = 0;

    function renderInput() {
        input.innerText = '';

        Array.from(buffer).forEach((char, index) => {
            let span = document.createElement('span');
            span.textContent = char;
            if (index === cursorPosition) {
                span.className = 'cursor';
            }
            input.appendChild(span);
        });
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

    const moveCursorToEnd = () => {
        const range = document.createRange();
        const sel = window.getSelection();

        range.selectNodeContents(input);
        range.collapse(false); // collapse to end
        sel.removeAllRanges();
        sel.addRange(range);
    };

    terminal.addEventListener('click', () => {
        input.focus();
        moveCursorToEnd();
    });

    input.addEventListener("focus", () => {
        moveCursorToEnd();
    });

    input.addEventListener('keydown', async (e) => {
        if(e.key.length === 1) {
            buffer += e.key;
            ++cursorPosition;
            e.preventDefault();
        } else if (e.key === "ArrowLeft") {
            cursorPosition = Math.max(--cursorPosition, 0);
            e.preventDefault();
        } else if (e.key === "ArrowRight") {
            cursorPosition = Math.min(++cursorPosition, buffer.length);
            e.preventDefault();
        } else if (e.key === 'Backspace') {
            buffer = buffer.slice(0, -1);
            cursorPosition = Math.min(++cursorPosition, buffer.length);
            e.preventDefault();
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
                console.log(prompt);
            }
            return;
        }
        renderInput();
        cursorPosition === buffer.length ? 
        endCursor.className = 'cursor' : 
        endCursor.classList.remove('cursor');
        // if (cursorPosition === buffer.length) {
        //     endCursor.className = 'cursor'
        // } else {
        //     endCursor.classList.remove('cursor');
        // }
        console.log("Buffer length: ", buffer.length);
        console.log("Cursor position: ", cursorPosition);
    });

    newGameBtn.addEventListener('click', async () => {
        startGame();
    });
});