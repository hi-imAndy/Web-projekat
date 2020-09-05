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
				newAmenitie:{}
		    }
	},
	template: ` 
	<div>
		<div class = "container">
			<div class = "row" style="margin-top: 20px;">
				<div class="col-md-auto">
					<table class="table table-image table-hover">
							  <thead>
							    <tr>
							      <th scope="col"></th>
							      <th scope="col">ID</th>
							      <th scope="col">Status</th>
							      <th scope="col">Price</th>
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
											<td>{{a.pricePerNight + " RSD"}}</td>
											<td>{{a.user.firstName + " " + a.user.lastName }}</td>
											<td>{{a.location.address.city.name}}</td>
											<td>{{a.location.address.street}} {{a.location.address.number}}</td>
										 </tr>
							  </tbody>
						</table>   
					</div>
					<div class="col-md-auto" style = "margin-left:20px">
						<div class = "row">
							<button type="button" class="btn btn-outline-primary" v-on:click="sortApartments('asc')">Sort by price (asc)</button>
						</div>
						<div class="row" style = "margin-top: 20px">
							<button type="button" class="btn btn-outline-primary" v-on:click="sortApartments('desc')">Sort by price (des)</button>
						</div>
					</div>
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
		</div>
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
    	}
	}
});