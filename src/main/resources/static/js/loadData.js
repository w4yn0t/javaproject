function loadHeroes() {
    fetch('/api/dota/load/heroes', {
        method: 'POST'
    })
    .then(response => response.text())
    .then(message => {
        alert(message);
        location.reload();
    })
    .catch(error => {
        alert('Ошибка: ' + error);
    });
}

function loadPlayer() {
    let accountId = document.getElementById('accountId').value;
    if (!accountId) {
        alert("Введите accountId");
        return;
    }
    let params = new URLSearchParams();
    params.append("accountId", accountId);

    fetch('/api/dota/load/players', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString()
    })
    .then(response => response.text())
    .then(message => {
        alert(message);
        location.reload();
    })
    .catch(error => {
        alert('Ошибка: ' + error);
    });
}

function loadHeroDurations() {
    let heroName = document.getElementById('heroName').value.trim();
    if (!heroName) {
        alert("Введите имя героя");
        return;
    }
    let params = new URLSearchParams();
    params.append("heroName", heroName);

    fetch('/api/dota/load/heroDurations', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString()
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Ошибка запроса");
        }
        return response.json();
    })
    .then(data => {
        let tableBody = document.getElementById('heroDurationsTable');
        tableBody.innerHTML = "";

        if (data.length === 0) {
            tableBody.innerHTML = "<tr><td colspan='4'>Нет данных</td></tr>";
            document.getElementById('totalGames').textContent = "-";
            document.getElementById('averageWinRate').textContent = "-";
            return;
        }

        let totalGames = 0;
        let totalWins = 0;

        data.forEach(hero => {
            let durationStart = parseInt(hero.durationBin);
            let durationEnd = durationStart + 5;

            totalGames += hero.gamesPlayed;
            totalWins += hero.wins;

            let row = document.createElement('tr');
            row.innerHTML = `
                <td>${durationStart} - ${durationEnd} минут</td>
                <td>${hero.gamesPlayed}</td>
                <td>${hero.wins}</td>
                <td>${hero.winrate} %</td>
            `;
            tableBody.appendChild(row);
        });

        let averageWinRate = totalGames > 0 ? ((totalWins / totalGames) * 100).toFixed(2) : 0;

        document.getElementById('totalGames').textContent = totalGames;
        document.getElementById('averageWinRate').textContent = averageWinRate;
    })
    .catch(error => {
        alert('Ошибка: ' + error);
    });
}


