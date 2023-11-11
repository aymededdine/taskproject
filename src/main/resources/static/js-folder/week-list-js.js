const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

function generateLike(id) {
	fetch(`/weekly-tasks/generate-like/${id}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			[csrfHeader]: csrfToken
		},
		body: JSON.stringify({ id: id }),
	})
		.then(response => {
			if (response.status == 417) {
				window.location.href = "/weekly-tasks/create"
			}
			if (response.ok) {
				window.location.href = "/weekly-tasks/current";
			} else {
				return response.text().then(errorMessage => {
					console.error('Error:', errorMessage);
				});
			}
		})
		.catch(error => {
			console.error('Fetch error: ', error);
		});
}

function generateBased(id) {
	fetch(`/weekly-tasks/generate-based/${id}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			[csrfHeader]: csrfToken
		},
		body: JSON.stringify({ id: id }),
	})
		.then(response => {
			if (response.ok) {
				window.location.href = "/weekly-tasks/create";
			} else {
				return response.text().then(errorMessage => {
					console.error('Error:', errorMessage);
				});
			}
		})
		.catch(error => {
			console.error('Fetch error: ', error);
		});
}