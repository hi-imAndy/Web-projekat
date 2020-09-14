Vue.component("admin", {
	data: function () {
		    return {
		    	users:{},
		    	apartments:{},
		    	selectedApartment:{},
		    	searchUsername:"",
				searchRole:"",
				searchGender:"",
				usernameChecked:false,
				roleChecked:false,
				genderChecked:false,
				allAmenities:{},
				selectedAmenitie:{},
				newAmenitie:{},
				label: "",
				selectedDate: {},
				allCities:{},
				filter:{},
				filterAmenities:[]
		    }
	},
	template: ` 
	<div>
		<div class = "container">
			<div class = "row justify-content-md-between" style="margin-top: 20px;">
				
				<div class = "col-md-auto">
					<h3>All apartments:</h3>
				</div>
				
				<div class = "col-md-auto">
						<button class = "btn btn-warning" v-on:click = "editApartment" data-toggle="modal" data-target="#editModal" >Edit apartment</button>
						<button class = "btn btn-danger" v-on:click = "deleteApartment">Delete apartment</button>
				</div>
			</div>
			<div class = "row" style = "margin-top: 15px">
				<div class = "col-1" style="margin-top:30px"><button class="btn btn-outline-secondary" v-on:click="filterApartments">Filter:</button></div>
				<div class = "col-2" style="margin-top:30px">
					<select  class="browser-default custom-select" v-model="filter.type">
						<option disabled selected value> -- select type -- </option>
						<option value= "ENTIRE_APARTMENT">Entire apartment</option>
						<option value= "ROOM">Room</option>
					</select>
					
				</div>
				<div class = "col-2">
					<select  class="browser-default custom-select" v-model="filter.status" style = "margin-top:30px">
						<option disabled selected value> -- select status -- </option>
						<option value= "ACTIVE">Active</option>
						<option value = "INACTIVE">Inactive</option>
					</select>
				</div>
				<div class = "col=3">
					<select class="browser-default custom-select" multiple v-model="filterAmenities">
						<option v-for="am in allAmenities" v-bind:value="am">{{am.name}}</option>
					</select>
				</div>
			</div>
			<div class = "row" style="margin-top: 20px;">	
				<div class="col-md-auto">
					<table class="table table-image table-hover">
							  <thead>
							    <tr>
							      <th scope="col">
							      	<button type="button" class="btn btn-outline-primary" v-on:click="sortApartments('asc')">Sort(a)</button>
							      	<button type="button" class="btn btn-outline-primary" v-on:click="sortApartments('desc')">Sort(d)</button>
							      </th>
							      <th scope="col">ID</th>
							      <th scope="col">Status</th>
							      <th scope="col">Type</th>
							      <th scope="col">Price</th>
							      <th scope="col">Rooms</th>
							      <th scope="col">Host</th>
							      <th scope="col">Location</th>
							      <th scope="col">Address</th>
							    </tr>
							  </thead>
							  <tbody>
										<tr v-for="a in apartments" v-on:click="selectApartment(a)"   v-bind:class = "{selectedApartment : selectedApartment.id === a.id}">
											<td><img v-bind:src="a.pictures[0]" class="img-fluid img-thumbnail " width="250" height="100"></td>
											<td>{{a.id }}</td>
											<td>{{a.status}}</td>
											<td>{{a.apartmentType}}</td>
											<td>{{a.pricePerNight + " RSD"}}</td>
											<td>{{a.numberOfRooms}}</td>
											<td>{{a.user.firstName + " " + a.user.lastName }}</td>
											<td>{{a.location.address.city.name}}</td>
											<td>{{a.location.address.street}} {{a.location.address.number}}</td>
										 </tr>
							  </tbody>
						</table>   
					</div>
			</div>
		
		<div class = "row justify-content-end" style="margin-top: 20px;">
				<div class="col-2 custom-control custom-checkbox">
				    <input type="checkbox" class="custom-control-input" id="defaultUnchecked1" v-model="usernameChecked">
				    <label class="custom-control-label" for="defaultUnchecked1">Search by username</label>
				</div>
				<div class="col-1 custom-control custom-checkbox">
				    <input type="checkbox" class="custom-control-input" id="defaultUnchecked2" v-model="roleChecked">
				    <label class="custom-control-label" for="defaultUnchecked2">Role</label>
				</div>
				<div class="col-1 custom-control custom-checkbox">
				    <input type="checkbox" class="custom-control-input" id="defaultUnchecked3" v-model="genderChecked">
				    <label class="custom-control-label" for="defaultUnchecked3">Gender</label>
				</div>
			</div>
			<div class = "row justify-content-md-end">
				<div class = "col-2">
					<input type = "text" v-model="searchUsername" placeholder="Username.." v-bind:disabled="usernameChecked==false">
				</div>
				<div class = "col-1">
					<select v-model="searchRole" v-bind:disabled="roleChecked==false">
						<option value = "GUEST">Guest</option>
						<option value = "HOST">Host</option>
						<option value = "ADMINISTRATOR">Admin</option>
					</select>
				</div>
				<div class = "col-1">
					<select v-model="searchGender" v-bind:disabled="genderChecked==false">
						<option value = "MALE">Male</option>
						<option value = "FEMALE">Female</option>
					</select>
				</div>
		    </div>
		    <div class = "row justify-content-md-end">
		    	<div class = "col-2">
					<button type="button" class="btn btn-outline-primary" v-on:click="search">Search</button>
				</div>
		    </div>
			<div class = "row justify-content-md-center">
				<div class = "col-md-auto">
					<h2>List of all users</h2>
				</div>
			</div>
			<div class = "row table-responsive">
				<div class = "col">
					<table class = "table table-bordered table-hover" >
						<caption>List of all users</caption>
						<tr class="table-info">
							<th scope="col">Username</th>
							<th scope="col">Password</th>
							<th scope="col">First name</th>
							<th scope="col">Last name</th>
							<th scope="col">Gender</th>
							<th scope="col">Role</th>
						</tr>
						<tr v-for="u in users">
							<td>{{u.username }}</td>
							<td>{{u.password }}</td>
							<td>{{u.firstName }}</td>
							<td>{{u.lastName }}</td>
							<td>{{u.gender}}</td> 
							<td>{{u.role}}</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class = "row justify-content-center" style="margin-top: 20px;">
			<div class = "col-md-auto"><h2>List of all amenities</h2></div>
		</div>
		<div class = "row justify-content-center" style="margin-top: 10px;">
			<div class = "col-md-auto">
				<div class = "row justify-content-center">
					<div class="table-responsive-sm">
					<table class="table table-sm table-bordered table-hover" style="text-align:center">
						<thead class="thead-light">
							<th>ID</th>
							<th>Name</th>
						</thead>
						<tbody>
							<tr v-for="am in allAmenities" v-on:click="selectAmenitie(am)">
								<td>{{am.id}}</td>
								<td>{{am.name}}</td>
							</tr>
						</tbody>
					</table>
					</div>
				</div>
			</div>
			<div class = "col-md-auto" style="margin-left:20px;">
				<div class = "row justify-content-center"><h6>Edit amenities:</h6></div>
				<div class = "row justify-content-center">
					ID: <input type = "text" disabled style="margin-right:5px;" v-model="selectedAmenitie.id"/>
					Name: <input type = "text" v-model="selectedAmenitie.name"/>
				</div>
				<div class = "row justify-content-center" style="margin-top: 10px;">
					<div class = "col-md-auto"><button class="btn btn-success" v-on:click="editAmenitie">Save</button></div>
					<div class = "col-md-auto"><button class="btn btn-danger" v-on:click="deleteAmenitie">Delete</button></div>
					<div class = "col-md-auto"><button class="btn btn-secondary" v-on:click="cancelEdit">Cancel</button></div>
				</div>
				<div class = "row justify-content-center" style="margin-top: 100px;"><h6>Create new amenitie:</h6></div>
				<div class = "row justify-content-center">
					ID: <input type = "text" style="margin-right:5px;" v-model="newAmenitie.id"/>
					Name: <input type = "text" v-model="newAmenitie.name"/>
				</div>
				<div class = "row justify-content-center" style="margin-top: 10px;">
					<div class = "col-md-auto"><button class="btn btn-success" v-on:click="createAmenitie">Create</button></div>
					<div class = "col-md-auto"><button class="btn btn-secondary" v-on:click="cancelCreate" >Cancel</button></div>
				</div>
			</div>
		</div>
		
		<form action = "" class = "main-form needs-validation" novalidate>
				<div id="editModal" class="modal fade"> 
					<div class = "modal-dialog modal-dialog-centered">
						<div class = "modal-content">
							<div class = "modal-header">
								<h2 class = "modal-title" style="margin-left:120px">Edit apartment</h2>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span>&times</span>
								</button>
							</div>
							<div class = "modal-body">
								<div class = "container">
							<div class = "row justify-content-md-center">
								<div class="col-md-auto">ID:</div>
								<div class="col-md-auto"><input type = "text" v-model="selectedApartment.id" disabled></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class="col-md-auto" style="text-align: center;">Apartment type:</div>
								<div class = "col-md-auto">
									<select class="browser-default custom-select" v-model="selectedApartment.apartmentType">
										<option value="ENTIRE_APARTMENT">Entire apartment</option>
										<option value="ROOM">Room</option>
								  	</select>
								</div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Number of rooms:</div>
								<div class="col-md-auto"><input type = "number" value="1" min="1" max="10" step="1" v-model="selectedApartment.numberOfRooms"></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto" >Number of guests:</div>
								<div class="col-md-auto"><input type = "number" value="1" min="1" max="10" step="1" v-model="selectedApartment.numberOfGuests"></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Latitude:</div>
								<div class = "col-md-auto"><input type = "text" v-if = "selectedApartment.location != undefined" v-model="selectedApartment.location.latitude"/></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Longitude:</div>
								<div class = "col-md-auto"><input type = "text" v-if = "selectedApartment.location != undefined" v-model="selectedApartment.location.longitude"/></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Street:</div>
								<div class = "col-md-auto"><input type = "text" v-if = "selectedApartment.location != undefined" v-model="selectedApartment.location.address.street"/></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Number:</div>
								<div class="col-md-auto"><input type = "number" value="1" min="1" max="1000" step="1" v-if = "selectedApartment.location != undefined" v-model="selectedApartment.location.address.number"></div> 
								<div class = "col-md-auto">City:</div>
								<div class = "col-md-auto">
									<select class="browser-default custom-select" v-if="selectedApartment.location != undefined" v-model="selectedApartment.location.address.city">
										<option v-for="c in allCities" :value="c">{{c.name}}</option>
								  	</select>
								</div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">All available dates:</div>
							</div>
							<div class = "row justify-content-md-center" style="border:1px solid #00BFFF; padding:10px">
									<div class="col-md-auto" >
										<input type = "date" v-on:change="addDateToSelected" v-model="selectedDate" />
										<input type = "text" readonly v-if = "selectedApartment.allDates != undefined" v-model="selectedApartment.allDates "/>
									</div>
									<div class = "col-md-auto">
										<button class ="btn btn-outline-secondary" v-on:click="clearDatesInSelected">Clear dates</button>
									</div>
							</div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">Price per night:</div>
								<div class = "col-md-auto"><input type = "text" v-model="selectedApartment.pricePerNight"/></div>
							<div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">Check in time:</div>
								<div class = "col-md-auto">
									 <input type = "text" value="14:00" placeholder="Initially: 14:00" v-model="selectedApartment.checkInTime"/>
								</div>
							</div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">Check out time:</div>
								<div class = "col-md-auto">
									 <input type = "text" value="22:00" placeholder="Initially: 22:00" v-model="selectedApartment.checkOutTime"/>
								</div>
							</div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">Status:</div>
								<div class = "col-md-auto">
									 <select class="browser-default custom-select" v-model="selectedApartment.status">
										<option value="ACTIVE">Active</option>
										<option value="INACTIVE">Inactive</option>
								  	</select>
								</div>
							</div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">
									<label for="picture">Select pictures:</label>
									<input type="file" id="picture" multiple="true" name="picture" accept="image/*" v-on:change="updateImagesForEdit">
								</div>
							</div>
							</br>
							<div style="border:1px solid #00BFFF; padding:5px">
								<div class = "row">
									<div class = "col">Amenities:</div>
								</div>
								<div class = "row justify-content-md-center">
									<ul>
										<li v-for="am in allAmenities">
											<input type="checkbox" v-if="selectedApartment.amenities != undefined && selectedApartment.amenities.some(amen => amen.id === am.id)" checked id="am.id" value="am" v-on:change="updateAmenitiesForEdit(am, $event)" ref="amenitiesSelected">
											<input type="checkbox" v-else id="am.id" value="am" v-on:change="updateAmenitiesForEdit(am, $event)" ref="amenitiesSelected">
											<label for="am.id"> {{am.name}}</label>
										</li>
									</ul>
								</div>
							</div>
							</br>
							<div class = "row">
								<div class = "col">
									<label style="color:red">{{label}}</label>
								</div>
							</div>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">
									<button class = "btn btn-primary" v-on:click="edit">Save</button>
								</div>
							</div>
							</div>
						</div>
					</div>
					</div>
				</div>
			</div>
			</div>
		</form>
	</div>
	`,
	mounted(){
		axios
		.get("/Project/rest/apartments/getAllApartments")
		.then(response => (this.apartments = response.data));
		
		axios
		.get("/Project/rest/users/getAllUsers")
		.then(response => (this.users = response.data));

		axios
		.get("/Project/rest/amenities/getAllAmenities")
		.then(response => (this.allAmenities = response.data));
		
		axios
		.get("/Project/rest/cities/getAllCities")
		.then(response => (this.allCities = response.data));
		
	},
	watch : {
		 filterAmenities(newAmenities){
		    	this.filter.amenities = newAmenities;
		    }
	},
	methods : {
		selectApartment : function(apartment){
			this.selectedApartment = apartment;
		},
		search : function(){
    		if(this.usernameChecked === true || this.roleChecked === true || this.genderChecked == true){
    			axios
        		.get("/Project/rest/users/searchUsers", {params: {username : this.searchUsername, role : this.searchRole, gender: this.searchGender, u : this.usernameChecked, r: this.roleChecked, g: this.genderChecked}})
        		.then(response => (this.users = response.data))
    		}
    	},
    	selectAmenitie : function(am){
    		this.selectedAmenitie = am;
    	},
    	deleteAmenitie : function(){
    			axios
        		.post("/Project/rest/amenities/deleteAmenitie", this.selectedAmenitie)
    			.then(response => {
    				if(response.data == false)
    					alert("Amenitie with that ID does not exist!");
    				else{
    					axios
    					.post("/Project/rest/apartments/deleteAmenitieInApartments", this.selectedAmenitie);
    					
    					this.selectedAmenitie = {};
    	        		
    	        		axios
    	        		.get("/Project/rest/amenities/getAllAmenities")
    	        		.then(response => (this.allAmenities = response.data));
    				}
    			});
    	},
    	createAmenitie: function(){
    		axios
    		.post("/Project/rest/amenities/addAmenitie", this.newAmenitie)
    		.then(response => {
    			if(response.data == false)
    				alert("Amenities with that ID already exists");
    			else{
    				axios
    	    		.get("/Project/rest/amenities/getAllAmenities")
    	    		.then(response => (this.allAmenities = response.data));
    				this.newAmenitie = {};
    			}
    		});
    	},
    	editAmenitie: function(){
    		axios
    		.post("/Project/rest/amenities/editAmenitie", this.selectedAmenitie)
    		.then(response => {
    			if(response.data == false)
    				alert("Amenities with that ID does not exist");
    		});
    		this.selectedAmenitie = {};
    	},
    	cancelEdit: function(){
    		this.selectedAmenitie = {};
    		axios
    		.get("/Project/rest/amenities/getAllAmenities")
    		.then(response => (this.allAmenities = response.data));
    	},
    	cancelCreate: function(){
    		this.newAmenitie = {};
    	},
    	sortApartments: function(sort){
    		var n = this.apartments.length;
    		var sortedApartments = [n+1];
    		
    		for(let i = 0; i < n; i++) {
    	        for(let j = i + 1; j < n; j++){
    	        	if(sort == 'asc'){
	    	            if(this.apartments[j].pricePerNight < this.apartments[i].pricePerNight) {
	    	                t = this.apartments[i];
	    	                this.apartments[i] = this.apartments[j];
	    	                this.apartments[j] = t;
	    	            }
    	        	}else if(sort == 'desc'){
    	        		if(this.apartments[j].pricePerNight > this.apartments[i].pricePerNight) {
	    	                t = this.apartments[i];
	    	                this.apartments[i] = this.apartments[j];
	    	                this.apartments[j] = t;
	    	            }
    	        	}
    	        }
    	        sortedApartments[i] = this.apartments[i];
    		}
    		this.apartments = sortedApartments;
    	},
    	editApartment : function(event){
			if(this.selectedApartment.id == undefined){
				alert("An apartment needs to be selected!");
				event.stopPropagation();
			}
			this.label = "";
		},
		deleteApartment : function(){
			if(this.selectedApartment.id == undefined)
				alert("An apartment needs to be selected!");
			else{
				axios
				.post("/Project/rest/apartments/deleteApartment", this.selectedApartment);
				alert("Apartment " + this.selectedApartment.id + " deleted!");
				
				axios
				.get("/Project/rest/apartments/getAllApartments")
				.then(response => (this.apartments = response.data));
			}
		},
		edit : function(){
			
			this.label = "";
			var i = 0;
			if(this.selectedApartment.apartmentType == null || this.selectedApartment.apartmentType == undefined || this.selectedApartment.apartmentType == ""){
				this.label += "Apartment type is required. ";
				i++;
			}
			if(this.selectedApartment.numberOfRooms == null || this.selectedApartment.numberOfRooms == undefined || this.selectedApartment.numberOfRooms == ""){
				this.label += "Number of rooms is required. ";
				i++;
			}
			if(this.selectedApartment.numberOfGuests == null || this.selectedApartment.numberOfGuests == undefined || this.selectedApartment.numberOfGuests == ""){
				this.label += "Number of guests is required. ";
				i++;
			}
			if(this.selectedApartment.location.latitude == null || this.selectedApartment.location.latitude == undefined || this.selectedApartment.location.latitude == ""){
				this.label += "Latitude is required. ";
				i++;
			}
			if(this.selectedApartment.location.longitude == null || this.selectedApartment.location.longitude == undefined || this.selectedApartment.location.longitude == ""){
				this.label += "Longitude is required. ";
				i++;
			}
			if(this.selectedApartment.location.address.street == null || this.selectedApartment.location.address.street == undefined || this.selectedApartment.location.address.street == ""){
				this.label += "Street is required. ";
				i++;
			}
			if(this.selectedApartment.location.address.number == null || this.selectedApartment.location.address.number == undefined || this.selectedApartment.location.address.number == ""){
				this.label += "Number is required. ";
				i++;
			}
			if(this.selectedApartment.location.address.city == null || this.selectedApartment.location.address.city == undefined || this.selectedApartment.location.address.city == ""){
				this.label += "City is required. ";
				i++;
			}
			if(this.selectedApartment.allDates == null || this.selectedApartment.allDates == undefined || this.selectedApartment.allDates == "" || this.selectedApartment.allDates == []){
				this.label += "Dates are required. ";
				i++;
			}
			if(this.selectedApartment.pricePerNight == null || this.selectedApartment.pricePerNight == undefined || this.selectedApartment.pricePerNight == ""){
				this.label += "Price per night is required. ";
				i++;
			}
			if(this.selectedApartment.checkInTime == null || this.selectedApartment.checkInTime == undefined || this.selectedApartment.checkInTime == ""){
				this.label += "Check in time is required. ";
				i++;
			}
			if(this.selectedApartment.checkOutTime == null || this.selectedApartment.checkOutTime == undefined || this.selectedApartment.checkOutTime == ""){
				this.label += "Check out time is required. ";
				i++;
			}
			
			if(i == 0){
				axios
				.post("/Project/rest/apartments/editApartment", this.selectedApartment);
				alert("Apartment " + this.selectedApartment.id + " edited!");
				
				axios
				.get("/Project/rest/apartments/getApartmentsByUsername", {params : {username : user.username}})
				.then(response => {
					this.myApartments = response.data;
					for(var i = 0; i < response.data.length; i++){
						if(response.data[i].status === "ACTIVE")
							this.activeApartments.push(response.data[i]);
						else
							this.inactiveApartments.push(response.data[i]);
					}
				});
				this.label = "";
			}
		},
		addDateToSelected : function(){
			if(this.selectedDate < this.minDate){
				alert("A date earlier than " + this.minDate + " can not be selected!");
			}else{
				if(this.selectedApartment.allDates.includes(this.selectedDate) == false)
					this.selectedApartment.allDates.push(this.selectedDate);
			}
		},
		clearDatesInSelected: function(){
			this.selectedApartment.allDates = [];
		},
		updateAmenitiesForEdit : function(value, $event){
			if($event.target.checked){
				if(!this.selectedApartment.amenities.includes(value))
					this.selectedApartment.amenities.push(value);
			}else{
				var newAm = [];
				for(var i = 0, a; a = this.selectedApartment.amenities[i]; i++){
					if(a != value)
						newAm.push(a);
				}
				this.selectedApartment.amenities = newAm;
			}
		},
		updateImagesForEdit : function(event){
    		var fileNames = [];
    		var files = event.target.files;
    		for(var i = 0, f; f = files[i]; i++){
    			fileNames.push("pictures\\" + f.name);
    		}
    		this.selectedApartment.pictures = fileNames;
    	},
    	filterApartments : function(){
    		axios
    		.post("/Project/rest/apartments/filterHost", this.filter)
    		.then(response => {
    			if(response.data != null){
    				this.apartments = response.data;
    			}
    		})
    	}
	}
});