function createAlert(message, callback) {
    const modal = document.createElement('div');
    modal.classList.add('modal');
    modal.innerHTML = `
            <div class="modal-content">
                <span class="close">&times;</span>
                <p>${message}</p>
            </div>
        `;

    document.body.appendChild(modal);
    const closeBtn = modal.querySelector('.close');
    closeBtn.addEventListener('click', () => {
        modal.remove();
        callback();
    });
}

function createConfirm(message, callback) {
    const modal = document.createElement('div');
    modal.classList.add('modal');
    modal.innerHTML = `
        <div class="modal-content">
            <p>${message}</p>
            <div class="confirm-container">
                <button class="confirm-btn">OK</button>
                <button class="cancel-btn">Cancel</button>
            </div>
        </div>
    `;
    document.body.appendChild(modal);

    const confirmBtn = modal.querySelector('.confirm-btn');
    const cancelBtn = modal.querySelector('.cancel-btn');

    confirmBtn.addEventListener('click', () => {
        modal.remove();
        callback(true);
    });

    cancelBtn.addEventListener('click', () => {
        modal.remove();
        callback(false);
    });
}