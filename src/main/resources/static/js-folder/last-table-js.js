function submitTask(id) {

	var result = window.confirm("Click OK to confirm submitting");

	if (result) {

		console.log(id);

		fetch(`/day-tasks/submit/${id}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ id: id }), // Convert the object to a JSON string
		})
			.then(response => {
				if (response.ok) {
					console.log('Task submitted successfully');
					window.location.reload();

				} else {
					return response.text().then(errorMessage => {
						console.error('Error submitting task:', errorMessage);
					});
				}
			})
			.catch(error => {
				console.error('Fetch error: ', error);
			});
	} else {
		console.log("submitting canceled");
	}
}

function withdrawTask(id) {
	console.log(id);

	var result = window.confirm("Do you really want to withdraw this Task?");

	if (result) {

		fetch(`/day-tasks/withdraw/${id}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ id: id }),
		})
			.then(response => {
				if (response.ok) {

					const li = document.querySelector('li');
					li.style.color = '#888';
					li.style.backgroundColor = 'f0f0f0';
					window.location.reload();
					console.log('Task withdrawed successfully');


				} else {
					return response.text().then(errorMessage => {
						console.error('Error withdrawing task:', errorMessage);
					});
				}
			})
			.catch(error => {
				console.error('Fetch error: ', error);
			});
	} else {
		console.log("withdrawing canceled");
	}
}

function submitWeek(id) {

	var result = window.confirm("Click OK to confirm submitting");

	if (result) {

		console.log(id);

		fetch(`/weekly-tasks/submit/${id}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ id: id }),
		})
			.then(response => {
				if (response.status == 417) {
					window.alert("You can't submit a non-competed week");
					throw new Error(`HTTP error! Status: ${response}`);
				}
				if (!response.ok) {
					throw new Error(`HTTP error! Status: ${response}`);
				}
				window.location.href = "/congratulations";
				console.log("Week Submitted Successfully")
			}).then(data => {
			})
			.catch(error => {
				console.error('Fetch error: ', error);
			});
	} else {
		console.log("submitting canceled");
	}

}
