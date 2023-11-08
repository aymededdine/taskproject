function generateLike(id) {
	fetch(`/weekly-tasks/generate-like/${id}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
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
			'Content-Type': 'application/json'
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