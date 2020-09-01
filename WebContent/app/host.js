Vue.component("host", {
	data: function () {
		    return {
		      amenities:{},
		      allCities:{},
		      newApartment:{},
		      location:{},
		      address:{},
		      city:{},
		      pictures:{},
		      startingDate:{},
		      endingDate:{},
		      newAmenities:[]
		    }
	},
	template: ` 
	<div>
		{{newApartment}}
		{{location}}
		{{address}}
		{{pictures}}
		{{newAmenities}}
		<div class="container" style="border: 1px solid gray">
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
					<div class="col">
						<input id = "daterange" name = "daterange" placeholder="Select date range.." v-on:change="updateDate" type="text"/>
					</div>
				</div>
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
						<option value="INACTIVE" selected>Inactive</option>
				  	</select>
				</div>
				<div class = "col-md-auto">
					<label for="picture">Select pictures:</label>
					<input type="file" id="picture" multiple="true" name="picture" accept="image/*" v-on:change="updateImages">
				</div>
			</div>
			</br>
			<div style="border:1px solid blue">
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
					<button class = "btn btn-primary">Create</button>
				</div>
			</div>
		</div>
	</div>
	`,
	mounted(){
		axios
		.get("/Project/rest/amenities/getAllAmenities")
		.then(response => (this.amenities = response.data));
		
		axios
		.get("/Project/rest/cities/getAllCities")
		.then(response => (this.allCities = response.data));
	},
	methods:{
		updateImages : function(event){
    		var fileNames = [];
    		var files = event.target.files;
    		for(var i = 0, f; f = files[i]; i++){
    			fileNames.push(f.name);
    		}
    		this.pictures = fileNames;
    	},
	updateDate : function(event){
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
	}
	}
});

$(function(){
	$('#daterange').daterangepicker({
		minDate: moment()
	}, function(start,end){
		this.startingDate = start.format('MM/DD/YYYY');
		this.endingDate = end.format('MM/DD/YYYY');
	}
	);
});