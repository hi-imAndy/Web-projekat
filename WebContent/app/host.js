Vue.component("host", {
	data: function () {
		    return {
		      currentUser:{},
		      myApartments:{},
		      activeApartments:[],
		      inactiveApartments:[],
		      selectedApartment:{},
		      amenities:{},
		      allCities:{},
		      newApartment:{},
		      location:{},
		      address:{},
		      pictures:{},
		      newAmenities:[],
		      allDates:[],
		      selectedDate:{},
		      minDate:{},
		      filterAmenities:[],
		      filter:{}
		    }
	},
	template: ` 
	<div>
		<div class="container">
			<div class = "row justify-content-md-between">
				<div class = "col-md-auto">
					<h3>My ACTIVE apartments:</h3>
				</div>
				<div class = "col-md-auto">
						<button class = "btn btn-primary" data-toggle="modal" data-target="#createModal">New apartment</button>
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
						<option v-for="am in amenities" v-bind:value="am">{{am.name}}</option>
					</select>
				</div>
			</div>
			<div class = "row" style="margin-top: 20px;">
				<div class="col-md-auto">
					<table class="table table-image table-hover">
							  <thead>
							    <tr>
							      <th scope="col">
								      <button class = "btn btn-outline-info" v-on:click="sortApartments('asc')">Sort(a)</button>
								      <button class = "btn btn-outline-info" style="margin-left:20px" v-on:click="sortApartments('desc')">Sort(d)</button>
							      </th>
							      <th scope="col">ID</th>
							      <th scope="col">Status</th>
							      <th scope="col">Type</th>
							      <th scope="col">Rooms</th>
							      <th scope="col">Price</th>
							      <th scope="col">Host</th>
							      <th scope="col">Location</th>
							      <th scope="col">Address</th>
							     </tr>
							  </thead>
							  <tbody>
										<tr v-for="a in activeApartments" v-on:click="selectApartment(a)"   v-bind:class = "{selectedApartment : selectedApartment.id === a.id}">
											<td><img v-bind:src="a.pictures[0]" class="img-fluid img-thumbnail " width="250" height="100"></td>
											<td>{{a.id }}</td>
											<td>{{a.status}}</td>
											<td>{{a.apartmentType}}</td>
											<td>{{a.numberOfRooms}}</td>
											<td>{{a.pricePerNight + " RSD"}}</td>
											<td>{{a.user.firstName + " " + a.user.lastName }}</td>
											<td>{{a.location.address.city.name}}</td>
											<td>{{a.location.address.street}} {{a.location.address.number}}</td>
										 </tr>
							  </tbody>
						</table>   
					</div>
			</div>
			
			
			<div class = "row">
				<h3>My INACTIVE apartments:</h3>
			</div>
			<div class = "row" style="margin-top: 20px;">
				<div class="col-md-auto">
					<table class="table table-image table-hover">
							  <thead>
							    <tr>
							      <th scope="col"></th>
							      <th scope="col">ID</th>
							      <th scope="col">Status</th>
							      <th scope="col">Type</th>
							      <th scope="col">Rooms</th>
							      <th scope="col">Price</th>
							      <th scope="col">Host</th>
							      <th scope="col">Location</th>
							      <th scope="col">Address</th>
							    </tr>
							  </thead>
							  <tbody>
										<tr v-for="a in inactiveApartments" v-on:click="selectApartment(a)"   v-bind:class = "{selectedApartment : selectedApartment.id === a.id}">
											<td><img v-bind:src="a.pictures[0]" class="img-fluid img-thumbnail " width="250" height="100"></td>
											<td>{{a.id }}</td>
											<td>{{a.status}}</td>
											<td>{{a.apartmentType}}</td>
											<td>{{a.numberOfRooms}}</td>
											<td>{{a.pricePerNight + " RSD"}}</td>
											<td>{{a.user.firstName + " " + a.user.lastName }}</td>
											<td>{{a.location.address.city.name}}</td>
											<td>{{a.location.address.street}} {{a.location.address.number}}</td>
										 </tr>
							  </tbody>
						</table>   
					</div>
				</div>
			</div>
			
			<form action = "" class = "main-form needs-validation" novalidate>
				<div id="createModal" class="modal fade"> 
					<div class = "modal-dialog modal-dialog-centered">
						<div class = "modal-content">
							<div class = "modal-header">
								<h2 class = "modal-title" style="margin-left:120px">New apartment</h2>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span>&times</span>
								</button>
							</div>
							<div class = "modal-body">
								<div class = "container">
							<div class = "row justify-content-md-center">
								<div class="col-md-auto">ID:</div>
								<div class="col-md-auto"><input type = "text" v-model="newApartment.id" ></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class="col-md-auto" style="text-align: center;">Apartment type:</div>
								<div class = "col-md-auto">
									<select class="browser-default custom-select" v-model="newApartment.apartmentType">
										<option value="ENTIRE_APARTMENT">Entire apartment</option>
										<option value="ROOM">Room</option>
								  	</select>
								</div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Number of rooms:</div>
								<div class="col-md-auto"><input type = "number" value="1" min="1" max="10" step="1" v-model="newApartment.numberOfRooms"></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto" >Number of guests:</div>
								<div class="col-md-auto"><input type = "number" value="1" min="1" max="10" step="1" v-model="newApartment.numberOfGuests"></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Latitude:</div>
								<div class = "col-md-auto"><input type = "text" v-model="location.latitude"/></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Longitude:</div>
								<div class = "col-md-auto"><input type = "text" v-model="location.longitude"/></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Street:</div>
								<div class = "col-md-auto"><input type = "text" v-model="address.street"/></div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">Number:</div>
								<div class="col-md-auto"><input type = "number" value="1" min="1" max="1000" step="1" v-model="address.number"></div> 
								<div class = "col-md-auto">City:</div>
								<div class = "col-md-auto">
									<select class="browser-default custom-select" v-model="address.city">
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
										<input type = "date" v-on:change="addDate" v-model="selectedDate" />
										<input type = "text" readonly v-model="allDates"/>
									</div>
									<div class = "col-md-auto">
										<button class ="btn btn-outline-secondary" v-on:click="clearDates">Clear dates</button>
									</div>
							</div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">Price per night:</div>
								<div class = "col-md-auto"><input type = "text" v-model="newApartment.pricePerNight"/></div>
							<div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">Check in time:</div>
								<div class = "col-md-auto">
									 <input type = "text" value="14:00" placeholder="Initially: 14:00" v-model="newApartment.checkInTime"/>
								</div>
							</div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">Check out time:</div>
								<div class = "col-md-auto">
									 <input type = "text" value="22:00" placeholder="Initially: 22:00" v-model="newApartment.checkOutTime"/>
								</div>
							</div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">Status:</div>
								<div class = "col-md-auto">
									 <select class="browser-default custom-select" v-model="newApartment.status">
										<option value="ACTIVE">Active</option>
										<option value="INACTIVE">Inactive</option>
								  	</select>
								</div>
							</div>
							<div class = "row justify-content-md-center" style="margin-top:10px">
								<div class = "col-md-auto">
									<label for="picture">Select pictures:</label>
									<input type="file" id="picture" multiple="true" name="picture" accept="image/*" v-on:change="updateImages">
								</div>
							</div>
							</br>
							<div style="border:1px solid #00BFFF; padding:5px">
								<div class = "row">
									<div class = "col">Amenities:</div>
								</div>
								<div class = "row justify-content-md-center">
									<ul>
										<li v-for="am in amenities">
											<input type="checkbox" id="am.id" value="am" v-on:change="updateAmenities(am, $event)" ref="amenitiesSelected">
											<label for="am.id"> {{am.name}}</label>
										</li>
									</ul>
								</div>
							</div>
							</br>
							<div class = "row justify-content-md-center">
								<div class = "col-md-auto">
									<button class = "btn btn-primary" v-on:click="create">Create</button>
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
										<li v-for="am in amenities">
											<input type="checkbox" v-if="selectedApartment.amenities != undefined && selectedApartment.amenities.some(amen => amen.id === am.id)" checked id="am.id" value="am" v-on:change="updateAmenitiesForEdit(am, $event)" ref="amenitiesSelected">
											<input type="checkbox" v-else id="am.id" value="am" v-on:change="updateAmenitiesForEdit(am, $event)" ref="amenitiesSelected">
											<label for="am.id"> {{am.name}}</label>
										</li>
									</ul>
								</div>
							</div>
							</br>
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
	methods:{
		create: function(){
			this.location.address = this.address;
			newPictures = [];
			for(var i = 0; i < this.pictures.length; i++){
				newPictures[i] = "pictures\\" + this.pictures[i];
			}
			var ap = {id : this.newApartment.id, apartmentType: this.newApartment.apartmentType, numberOfRooms:this.newApartment.numberOfRooms , numberOfGuests:this.newApartment.numberOfGuests, location: this.location, allDates: this.allDates, availableDates: this.allDates ,user : home.currentUser, pictures: newPictures, pricePerNight: this.newApartment.pricePerNight, checkInTime: this.newApartment.checkInTime, checkOutTime: this.newApartment.checkOutTime, status: this.newApartment.status, amenities: this.newAmenities};
			
			axios
			.post("/Project/rest/apartments/addNewApartment", ap)
			.then(response => {
				if(response.data == false)
					alert("Apartment with that ID already exists!");
				else{
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
					alert("New apartment created!");
				}
			});
		},
		updateImages : function(event){
    		var fileNames = [];
    		var files = event.target.files;
    		for(var i = 0, f; f = files[i]; i++){
    			fileNames.push(f.name);
    		}
    		this.pictures = fileNames;
    	},
    	updateImagesForEdit : function(event){
    		var fileNames = [];
    		var files = event.target.files;
    		for(var i = 0, f; f = files[i]; i++){
    			fileNames.push("pictures\\" + f.name);
    		}
    		this.selectedApartment.pictures = fileNames;
    	},
		updateAmenities : function(value, $event){
			if($event.target.checked){
				if(!this.newAmenities.includes(value))
					this.newAmenities.push(value);
			}else{
				var newAm = [];
				for(var i = 0, a; a = this.newAmenities[i]; i++){
					if(a != value)
						newAm.push(a);
				}
				this.newAmenities = newAm;
			}
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
		addDate : function(){
			if(this.selectedDate < this.minDate){
				alert("A date earlier than " + this.minDate + " can not be selected!");
			}else{
				if(this.allDates.includes(this.selectedDate) == false)
					this.allDates.push(this.selectedDate);
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
		clearDates: function(){
			this.allDates = [];
		},
		clearDatesInSelected: function(){
			this.selectedApartment.allDates = [];
		},
		selectApartment : function(apartment){
			this.selectedApartment = apartment;
		},
		edit : function(){
			
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
		},
		deleteApartment : function(){
			if(this.selectedApartment.id == undefined)
				alert("An apartment needs to be selected!");
			else{
				axios
				.post("/Project/rest/apartments/deleteApartment", this.selectedApartment);
				alert("Apartment " + this.selectedApartment.id + " deleted!");
				
				if(this.selectedApartment.status === 'ACTIVE'){
					var index = this.activeApartments.indexOf(this.selectedApartment);
					this.activeApartments.slice(index,1);
				}else{
					var index = this.inactiveApartments.indexOf(this.selectedApartment);
					this.inactiveApartments.slice(index,1);
				}
				
			}
		},
		editApartment : function(event){
			if(this.selectedApartment.id == undefined){
				alert("An apartment needs to be selected!");
				event.stopPropagation();
			}
		},
		sortApartments: function(sort){
    		var n = this.activeApartments.length;
    		var sortedApartments = [n+1];
    		
    		for(let i = 0; i < n; i++) {
    	        for(let j = i + 1; j < n; j++){
    	        	if(sort == 'asc'){
	    	            if(this.activeApartments[j].pricePerNight < this.activeApartments[i].pricePerNight) {
	    	                t = this.activeApartments[i];
	    	                this.activeApartments[i] = this.activeApartments[j];
	    	                this.activeApartments[j] = t;
	    	            }
    	        	}else if(sort == 'desc'){
    	        		if(this.activeApartments[j].pricePerNight > this.activeApartments[i].pricePerNight) {
	    	                t = this.activeApartments[i];
	    	                this.activeApartments[i] = this.activeApartments[j];
	    	                this.activeApartments[j] = t;
	    	            }
    	        	}
    	        }
    	        sortedApartments[i] = this.activeApartments[i];
    		}
    		this.activeApartments = sortedApartments;
    	},
    	filterApartments : function(){
    		axios
    		.post("/Project/rest/apartments/filterHost", this.filter)
    		.then(response => {
    			if(response.data != null){
    				this.myApartments = response.data;
    				this.activeApartments = [];
    				this.inactiveApartments = [];
    				for(var i = 0; i < response.data.length; i++){
    					if(response.data[i].status === "ACTIVE")
    						this.activeApartments.push(response.data[i]);
    					else
    						this.inactiveApartments.push(response.data[i]);
    				}
    			}
    		})
    	}
	},
	mounted(){
		this.minDate = moment().format("YYYY-MM-DD");
		
		axios
		.get("/Project/rest/users/currentUser")
		.then(response => (this.currentUser = response.data));
		
		axios
		.get("/Project/rest/amenities/getAllAmenities")
		.then(response => (this.amenities = response.data));
		
		axios
		.get("/Project/rest/cities/getAllCities")
		.then(response => (this.allCities = response.data));
	},
	watch: {
		currentUser(user) {
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
	    },
	    filterAmenities(newAmenities){
	    	this.filter.amenities = newAmenities;
	    }
	},
	 filters: {
	    	dateFormat: function (value, format) {
	    		var parsed = moment(value);
	    		return parsed.format(format);
	    	}
	   	}
});
