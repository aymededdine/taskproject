
		function content() {
			return "";
		}



		var springTasks = [[${map}]];



		var tasksByDay = {
			MONDAY: [],
			TUESDAY: [],
			WEDNESDAY: [],
			THURSDAY: [],
			FRIDAY: [],
			SATURDAY: [],
			SUNDAY: [],
		};


		if (springTasks != null) {
			for (const key in springTasks) {
				tasksByDay[key] = springTasks[key];
			}
			console.log(tasksByDay);
			appendBased();
		}

		function copyTo(referencedDay) {

			var selectElement = document.getElementById(referencedDay + "Select");
			var selectedOption = selectElement.options[selectElement.selectedIndex];
			var selectedDay = selectedOption.value;

			console.log(selectedDay);

			tasksByDay[selectedDay] = tasksByDay[referencedDay];

			var dayTasksElement = document.getElementById(selectedDay + "Tasks");
			dayTasksElement.innerHTML = "";

			for (const task of tasksByDay[selectedDay]) {
				let content = `
          			 <div class="task-item" style="display: flex; justify-content: space-between; align-items: center;">
    					<span>${task}</span>
    					<button id="updateButton" style="margin-left: auto;" onclick="updateTask('${selectedDay}', '${task}')">Update</button>
    					<button id="deleteButton" style="margin-left: 10px;" onclick="deleteTask('${selectedDay}', '${task}')">Delete</button>
					</div>

        `;
				dayTasksElement.innerHTML += content;
			}
			attachDeleteButtonListeners(selectedDay);
			var hiddenSpan = document.getElementById("copyfrom" + selectedDay);
			hiddenSpan.style.display = "block";
			updateTaskList();
		}












		function appendBased() {


			for (const key in tasksByDay) {

				if (tasksByDay[key].length === 0) {continue;}

				var dayTasksElement = document.getElementById(key + "Tasks");
				dayTasksElement.innerHTML = "";

				for (const task of tasksByDay[key]) {
					let content = `
          			 <div class="task-item" style="display: flex; justify-content: space-between; align-items: center;">
    					<span>${task}</span>
    					<button id="updateButton" style="margin-left: auto;" onclick="updateTask('${day}', '${task}')">Update</button>
    					<button id="deleteButton" style="margin-left: 10px;" onclick="deleteTask('${day}', '${task}')">Delete</button>
					</div>

        `;
					dayTasksElement.innerHTML += content;
				}
				attachDeleteButtonListeners(key);
			}



		}



		function addTask(day) {
			var taskName = prompt("Enter your task name:");

			if (taskName) {
				if (!tasksByDay[day].includes(taskName)) {
					tasksByDay[day].push(taskName);

					var hiddenSpan = document.getElementById("copyfrom" + day);
					hiddenSpan.style.display = "block";



				} else {
					alert("Task already exists");
				}
			}

			var dayTasksElement = document.getElementById(day + "Tasks");
			dayTasksElement.innerHTML = "";

			for (const task of tasksByDay[day]) {
				let content = `
          			<div class="task-item" style="display: flex; justify-content: space-between; align-items: center;">
    					<span>${task}</span>
    					<button id="updateButton" style="margin-left: auto;" onclick="updateTask('${day}', '${task}')">Update</button>
    					<button id="deleteButton" style="margin-left: 10px;" onclick="deleteTask('${day}', '${task}')">Delete</button>
					</div>`;
				dayTasksElement.innerHTML += content;
			}

			attachDeleteButtonListeners(day);

		}


		function deleteTask(day, taskName) {
			tasksByDay[day] = tasksByDay[day].filter(task => task !== taskName);
			updateTaskList(day);
		}

		function updateTask(day, task) {
			var updatedTask = prompt("Enter your new task name");

			if (updatedTask) {
				if (tasksByDay[day].includes(updatedTask)) {
					alert("Task already exists");
				} else {
					var index = tasksByDay[day].indexOf(task);

					if (index !== -1) {
						tasksByDay[day][index] = updatedTask;
						updateTaskList(day);
					} else {
						alert("Task not found");
					}
				}
			}
		}



		function updateTaskList(day) {
			var dayTasksElement = document.getElementById(day + "Tasks");
			dayTasksElement.innerHTML = "";

			for (const task of tasksByDay[day]) {
				let content = `
                    <div class="task-item" style="display: flex; justify-content: space-between; align-items: center;">
                        <span>${task}</span>
                        <button id="updateButton" style="margin-left: auto;" onclick="updateTask('${day}', '${task}')">Update</button>
                        <button id="deleteButton" style="margin-left: 10px;" onclick="deleteTask('${day}', '${task}')">Delete</button>
                    </div>
                `;
				dayTasksElement.innerHTML += content;
			}

			attachDeleteButtonListeners(day);

		}

		function attachDeleteButtonListeners(day) {
			var deleteButtons = document.querySelectorAll(`#${day}Tasks button`);
			deleteButtons.forEach(button => {
				button.addEventListener("click", function () {
					var taskName = this.parentNode.querySelector("span").textContent;
					deleteTask(day, taskName);
				});
			});
		}


		function saveTasks() {

			var result = window.confirm("Make sure that you are certain to complete these tasks. Every task you write will be recorded in the statistics and cannot be deleted");

			if (result) {

				var notNullDays = {};
				var blank = true;
				for (var day in tasksByDay) {
					if (tasksByDay[day].length > 0) {
						notNullDays[day] = tasksByDay[day]
						blank = false;
					}
				}

				if (blank == true) {
					alert("you have to add at least one task");
				} else {
					console.log(notNullDays);
					var tasksJSON = JSON.stringify(notNullDays);

					fetch('/weekly-tasks/save-tasks', {
						method: 'POST',
						headers: {
							'Content-Type': 'application/json'
						},
						body: tasksJSON,
					})
						.then(response => {
							if (response.ok) {
								console.log('Tasks saved successfuly');
								window.location.href = '/weekly-tasks/current';
							} else {
								console.error('Error saving tasks')
							}
						})
						.catch(error => {
							console.error('Error: ', error);
						})

				}
			} else {
				console.log("correct")
			}
		}

		function createWeek() {
			document.getElementById('selectDays').style.display = 'block';
			document.getElementById('selectDays').scrollIntoView({behavior: 'smooth'});
		}

		function reloadPage() {
			location.href = location.href;
		}