
window.addEventListener('load', function() {
	window.scrollTo(0, document.body.scrollHeight);
});

var colorMap = {
	"red": { background: "red", color: "white" },
	"yellow": { background: "yellow", color: "black" },
	"green": { background: "green", color: "white" }
};

function getRowValues(id, name, priority, status) {
	document.getElementById('taskId').value = id;
	document.getElementById('taskName').value = name;
	document.getElementById('taskStatus').value = status;
	document.getElementById('taskPriority').value = priority;

	document.getElementById('editForm').style.display = 'block';
	document.getElementById('overlay').style.display = 'block';


	editForm.scrollIntoView({ behavior: 'smooth' });
}

function closeForm() {
	document.getElementById('editForm').style.display = 'none';
	document.getElementById('overlay').style.display = 'none';
}

function submitTask(taskId) {

	fetch(`/tasks/submit/${taskId}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ id: taskId })
	})
		.then(response => {
			if (response.ok) {
				console.log('task submitted successfully');
				window.location.reload();
			} else {
				console.error('Error submitting task');
			}
		})
		.catch(error => {
			console.error('Error: ', error);
		});

}
function withdrawTask(taskId) {
	fetch(`/tasks/withdraw/${taskId}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ id: taskId })
	})
		.then(response => {
			if (response.ok) {
				console.log('task withdrawn successfully');
				window.location.reload();
			} else {
				console.error('Error withdrawing task');
			}
		})
		.catch(error => {
			console.error('Error: ', error);
		});
}

function updateTask(taskId) {
	var updatedName = prompt("Enter the new name");


	fetch(`/tasks/update/${taskId}`, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ name: updatedName })
	})
		.then(response => {
			if (response.ok) {
				console.log('Name updated successfully');
				window.location.reload();
			} else {
				console.error('Error updating name');
			}
		})
		.catch(error => {
			console.error('Error: ', error);
		});
}


function deleteTask(taskId) {


	fetch(`/tasks/delete/${taskId}`, {
		method: 'DELETE',
	})
		.then(response => {
			if (response.ok) {
				document.getElementById('testid').remove();
			} else {
				// Handle the error (e.g., show an error message)
			}
		})
		.catch(error => {
			console.error('Error:', error);
		});

	event.stopPropagation();
}
