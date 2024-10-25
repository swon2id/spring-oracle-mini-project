document.addEventListener('DOMContentLoaded', () => {
  const tbody = document.querySelector('tbody');
  const currentRowCount = tbody.rows.length;
  const rowsToAdd = 20 - currentRowCount;

  for (let i = 0; i < rowsToAdd; i++) {
    const emptyRow = document.createElement('tr');
    emptyRow.innerHTML = `
            <td></td><td></td><td></td><td></td>
        `;
    tbody.appendChild(emptyRow);
  }
});
