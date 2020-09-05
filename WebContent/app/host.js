Vue.component("host", {
	data: function () {
		    return {
		      currentUser:{},
		      myApartments:{},
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
		      minDate:{}
		    }
	},
	template: ` 
	<div>
		<div class="container">
			<div class = "row">
				<h3>My ACTIVE apartments:</h3>
			</div>
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
										<tr v-for="a in myApartments" v-on:click="selectApartment(a)"   v-bind:class = "{selectedApartment : selectedApartment.id === a.id}">
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
			</div>
			</div>
			<div class="container" style="border:1px solid gray">
			<div class = "row">
				<div class="col" style="text-align: center;"><h3>Add a new apartment</h3></div>
			</div>
			</br>
			<div class = "row justify-content-md-center">
				<div class="col-md-auto" style="text-align: center;">ID:</div>
				<div class="col-md-auto"><input type = "text" v-model="newApartment.id" ></div>
				<div class="col-md-auto" style="text-align: center;">Apartment type:</div>
				<div class = "col-md-auto">
					<select class="browser-default custom-select" v-model="newApartment.apartmentType">
						<option value="ENTIRE_APARTMENT">Entire apartment</option>
						<option value="ROOM">Room</option>
				  	</select>
				</div>
				<div class = "col-md-auto" style="text-align:center;">Number of rooms:</div>
				<div class="col-md-auto"><input type = "number" value="1" min="1" max="10" step="1" v-model="newApartment.numberOfRooms"></div>
				<div class = "col-md-auto" style="text-align:center;">Number of guests:</div>
				<div class="col-md-auto"><input type = "number" value="1" min="1" max="10" step="1" v-model="newApartment.numberOfGuests"></div>
			</div>
			</br>
			<div class = "row justify-content-md-center">
				<div class = "col-md-auto">Latitude:</div>
				<div class = "col-md-auto"><input type = "text" v-model="location.latitude"/></div>
				<div class = "col-md-auto">Longitude:</div>
				<div class = "col-md-auto"><input type = "text" v-model="location.longitude"/></div>
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
				<div class = "col-md-auto">
					<div class="col" style="border:1px solid #00BFFF; padding:5px">
						<input type = "date" v-on:change="addDate" v-model="selectedDate" />
						<input type = "text" readonly v-model="allDates"/>
						<button class ="btn btn-outline-secondary" v-on:click="clearDates">Clear dates</button>
					</div>
				</div>
			</div>
			<div class = "row justify-content-md-center" style="margin-top:10px">
				<div class = "col-md-auto">Price per night:</div>
				<div class = "col-md-auto"><input type = "text" v-model="newApartment.pricePerNight"/></div>
				<div class = "col-md-auto">Check in time:</div>
				<div class = "col-md-auto">
					 <input type = "text" value="14:00" placeholder="Initially: 14:00" v-model="newApartment.checkInTime"/>
				</div>
			</div>
			</br>
			<div class = "row justify-content-md-center">
				<div class = "col-md-auto">Check out time:</div>
				<div class = "col-md-auto">
					 <input type = "text" value="22:00" placeholder="Initially: 22:00" v-model="newApartment.checkOutTime"/>
				</div>
				<div class = "col-md-auto">Status:</div>
				<div class = "col-md-auto">
					 <select class="browser-default custom-select" v-model="newApartment.status">
						<option value="ACTIVE">Active</option>
						<option value="INACTIVE">Inactive</option>
				  	</select>
				</div>
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
				else
					alert("New apartment created!");
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
		addDate : function(){
			if(this.selectedDate < this.minDate){
				alert("A date earlier than " + this.minDate + " can not be selected!");
			}else{
				if(this.allDates.includes(this.selectedDate) == false)
					this.allDates.push(this.selectedDate);
			}
		},
		clearDates: function(){
			this.allDates = [];
		},
		selectApartment : function(apartment){
			this.selectedApartment = apartment;
		},
	},
	mounted(){
		this.$root.$on('userFromParent', (user) =>{
			this.currentUser = user;
		});
		
		this.minDate = moment().format("YYYY-MM-DD");
		
		axios
		.get("/Project/rest/apartments/getApartmentsByUsername", localStorage.currentUsername)
		.then(response => (this.myApartments = response.data));
		
		axios
		.get("/Project/rest/amenities/getAllAmenities")
		.then(response => (this.amenities = response.data));
		
		axios
		.get("/Project/rest/cities/getAllCities")
		.then(response => (this.allCities = response.data));
		
	}
});
