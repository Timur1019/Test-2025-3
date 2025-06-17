let chart = null;

document.getElementById('weatherForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const city = document.getElementById('city').value.trim();
    const error = document.getElementById('error');
    const ctx = document.getElementById('weatherChart').getContext('2d');
    error.textContent = ''; // Очищаем поле с ошибкой

    if (!city) {
        error.textContent = 'Пожалуйста, введите название города.';
        return;
    }

    try {
        const response = await fetch(`http://localhost:7070/weather?city=${encodeURIComponent(city)}`);
        if (!response.ok) throw new Error("Город не найден или ошибка сервера.");

        const data = await response.json();

        console.log("Ответ от сервера:", data);
        console.log("Город:", data.city);
        console.log("Часы:", data.hours);
        console.log("Температуры:", data.temperatures);

        if (chart !== null) {
            chart.destroy();
        }

        chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: data.hours,
                datasets: [{
                    label: `Температура в городе ${data.city}`,
                    data: data.temperatures,
                    borderColor: 'rgb(255, 99, 132)',
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderWidth: 2,
                    tension: 0.3,
                    fill: true
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    }
                },
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Час'
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'Температура (°C)'
                        }
                    }
                }
            }
        });

    } catch (err) {
        error.textContent = err.message || "Произошла неожиданная ошибка."; 
    }
});
