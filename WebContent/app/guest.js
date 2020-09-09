Vue.component("guest", {
	data: function () {
		    return {
			currentUser: {},
			apartments:{},
			selected:{},
			searchCriteria:{},
			reservationInfo:{}
		    }
	},
	template: ` 
	<div>
			
			
			
		<div class="container" style="margin-top:40px">
			<div class="row">
				<div class="col" style="text-align: center;">Location</div>
				<div class="col" style="text-align: center;">Check in</div>
				<div class="col" style="text-align: center;">Check out</div>
				<div class="col" style="text-align: center;">Price</div>
				<div class="col" style="text-align: center;">Guests</div>
				<div class="col" style="text-align: center;">Rooms</div>
			</div>
			<div class = "row">
				<div class="col">
					<select class="browser-default custom-select" v-model = "searchCriteria.location" >
						<option value="All">All</option>
						<option value="Novi Sad">Novi Sad</option>
						<option value="Beograd">Beograd</option>	
				  	</select></div>
				<div class="col"><input type="date" id="picker" class="form-control" v-model = "searchCriteria.startDate" ></div>
				<div class="col"><input type="date" id="picker" class="form-control" v-model = "searchCriteria.endDate"></div>
				<div class="col"><input type="text" placeholder = "minimal price" class = "form-control" style="vertical-align:middle;" v-model = "searchCriteria.pricePerNightMin" >
								 <input type="text" placeholder = "maximal price" class = "form-control" style="vertical-align:middle;" v-model = "searchCriteria.pricePerNightMax" >
				</div>
				<div class="col"><input type = "number"  min="1" max="25" step="1" class = "form-control" v-model = "searchCriteria.numberOfGuests"></div>
				<div class="col"><input type = "number" placeholder = "minimal number of rooms" value="1" min="1" max="25" step="1" class = "form-control" v-model = "searchCriteria.numberOfRoomsMin">
								<input type = "number" placeholder = "maximal number of rooms" value="1" min="1" max="25" step="1" class = "form-control" v-model = "searchCriteria.numberOfRoomsMax">
				</div>
			</div>
				<button type = "button" class="btn btn-primary" v-on:click="filterApartments(searchCriteria)">Filter appartments</button>
				<button type = "button" class="btn btn-primary" v-on:click="resetFilters()">Reset filters</button>
		</div>
		
		
			<div class="container">
				<table class="table table-hover">
				  <div class="row">
				    <div class="col-12">
						<table class="table table-image">
						  <thead>
						    <tr>
						      <th scope="col"><h1><b>All apartments</b></h1></th>
						      <th scope="col">Appartment ID</th>
						      <th scope="col">Host</th>
						      <th scope="col">Location</th>
						      <th scope="col">Address</th>
						    </tr>
						  </thead>
						  <tbody>
									<tr v-for="u in apartments" v-on:click="selectApartment(u)" v-if="u.status == 'ACTIVE' "  v-bind:class = "{selected : selected.id === u.id}" data-toggle="modal" data-target="#apartmentInfo">
										<td><img v-bind:src="u.pictures[0]" class="img-fluid img-thumbnail " width="250" height="100"></td>
										<td>{{u.id }}</td>
										<td>{{u.user.username }}</td>
										<td>{{u.location.address.city.name}}</td>
										<td>{{u.location.address.street}} {{u.location.address.number}}</td>
										
							    </tr>
			
						  </tbody>
						</table>   
				    </div>
				  </div>
				</table>
			</div>
			
			<div class="container">
				<table class="table table-hover">
				  <div class="row">
				    <div class="col-12">
						<table class="table table-image">
						  <thead>
						    <tr>
						      <th scope="col"><h1><b>Booked apartments</b></h1></th>
						      <th scope="col">Appartment ID</th>
						      <th scope="col">Host</th>
						      <th scope="col">Location</th>
						      <th scope="col">Address</th>
						    </tr>
						  </thead>
						  <tbody>
									<tr v-for="reservation in currentUser.reservations" v-on:click="selectApartment(reservation.reservedApartment)"  v-bind:class = "{selected : selected.id === reservation.reservedApartment.id}" data-toggle="modal" data-target="#apartmentInfo">
										<td><img v-bind:src="reservation.reservedApartment.pictures[0]" class="img-fluid img-thumbnail " width="250" height="100"></td>
										<td>{{reservation.reservedApartment.id }}</td>
										<td>{{reservation.reservedApartment.user.username }}</td>
										<td>{{reservation.reservedApartment.location.address.city.name}}</td>
										<td>{{reservation.reservedApartment.location.address.street}} {{reservation.reservedApartment.location.address.number}}</td>										
							    	</tr>
						  </tbody>
						</table>   
				    </div>
				  </div>
				</table>
			</div>
			
			
			
			
				
		
		
		
			<div id="apartmentInfo" class="modal fade"> 
				<div class = "modal-dialog modal-dialog-centered modal-xl">
				  <div class="modal-content">
						       <div class="modal-header">
								        <h1 class="modal-title">{{selected.id}} informations</h1>
								        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
								         	 <span aria-hidden="true">&times;</span>
								        </button>
							      </div>
							      <div class="modal-body">
							        	<table class="table table-image" >
											<div v-for="(p, index) in selected.pictures">
								    			<tr v-if="index % 3 == 0 ">
										        	<td>
										            	 <img class="d-block w-100" v-bind:src="selected.pictures[index]"  width="560" height="315" style = "margin-left : 15px" >
										       		 </td>
										        	<td>
										            	 <img class="d-block w-100" v-bind:src="selected.pictures[index+1]" v-if="selected.pictures[index+1] != null" width="560" height="315" style = "margin-left : 15px" >
										        	</td>
										       		 <td>
										            	 <img class="d-block w-100" v-bind:src="selected.pictures[index+2]" v-if="selected.pictures[index+2] != null" width="560" height="315" style = "margin-left : 15px " >
										       		 </td>
							    				</tr>        
										  </div>
				 			 	</table>

							   </br></br>
				
							
				
				
							<div class="row " style="margin-left :40px"  >
						  		<div class="col-sm" style="margin-left: 10px">
				             		  <h1><span class="badge badge-primary" style="margin-left: 10px" v-if = "selected.apartmentType === 'ENTIRE_APARTMENT'">Entire apartment</span></h1> 
							  		  <h1><span class="badge badge-primary" style="margin-left: 10px" v-if = "selected.apartmentType === 'ROOM'" >Room	</span></h1>      
						    	</div>
							    <div class="col-sm">
									<h1><span class="badge badge-success" style="margin-left: 10px">{{selected.numberOfRooms}} rooms</span></h1> 
							    </div>
							    <div class="col-sm">
									<h1><span class="badge badge-danger" style="margin-left: 10px">{{selected.numberOfGuests}} guests</span></h1> 
							    </div>
							    <div class="col-sm">
							       <h1><span class="badge badge-warning" style="margin-left: 10px">{{selected.pricePerNight}} RSD per night</span></h1> 
							    </div>
							    <div class="col-sm">
							    </div>
							    <div class="col-sm">
							    </div>
							    <div class="col-sm">
							    </div>					
							</div> 
					

							<ul>
								</br></br>
								<li v-for="am in selected.amenities">
									<input type="checkbox" id="am.id" value="am"  disabled="disabled" checked="checked">
									<label for="am.id"> {{am.name}}</label>
								</li>
							</ul>
							      </div>
	
								<div class = "container" v-for="(p, index) in selected.comments" v-if="p.approved">
									<td>
										<p><i> <b>{{index}}. </b>{{p.content}}</i></p>
										</br>
										<p><b>by Anonymus &#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160 rating:{{p.rating}}/10</b></p>
									</td>
								</div>
								
							      <div class="modal-footer">
											<div class="row justify-content-md-center">
												<div class="col-md-auto">
													<button type = "button" class="btn btn-primary" v-on:click="createReservation(reservationInfo)" >Book apartment</button>
													<button type = "button" class="btn btn-secondary" data-dismiss="modal">Close</button>
												</div>
											</div>
							     	 </div>
								</div>
							</div>
						</div>		
					</div>
				</div>
				</div>
				</div>
			</div>
	

	

			
	</div>
	`,
	mounted(){
		this.searchCriteria.location = null;
		this.searchCriteria.numberOfGuests = null;
		this.searchCriteria.startDate = null;
		this.searchCriteria.endDate = null;
		this.searchCriteria.numberOfRooms = null;
		this.searchCriteria.pricePerNightMin = null;
		this.searchCriteria.pricePerNightMax = null;
		this.searchCriteria.numberOfRoomsMin = null;
		this.searchCriteria.numberOfRoomsMax = null;
		
		
		axios
			.get("/Project/rest/apartments/getAllApartments")
			.then(response => {this.apartments = response.data;
			});
			
		axios
			.get("/Project/rest/users/currentUser")
			.then(response => {this.currentUser = response.data;
			});
	},
	methods : {
		selectApartment : function(apartment){
			this.selected = apartment;
			this.reservationInfo.apartment = this.selected;
			this.reservationInfo.user = this.currentUser;
			this.reservationInfo.startDate = this.searchCriteria.startDate;
			this.reservationInfo.endDate = this.searchCriteria.endDate;
			this.reservationInfo.reservationMessage = null;
			
			
},
		filterApartments : function(searchCriteria){
			if(Number(this.searchCriteria.pricePerNightMin)  > Number(this.searchCriteria.pricePerNightMax) &&  this.searchCriteria.pricePerNightMax != null  ){
				alert("Invalid price values");
			}
			else if(Number(this.searchCriteria.numberOfRoomsMin)  > Number(this.searchCriteria.numberOfRoomsMax) &&  this.searchCriteria.numberOfRoomsMax != null ){
				alert("Invalid number of rooms values");
			}
			else if(this.searchCriteria.location == null && this.searchCriteria.numberOfGuests == null && this.searchCriteria.endDate==null && this.searchCriteria.startDate == null && this.searchCriteria.numberOfRooms == null
					&& this.searchCriteria.pricePerNightMin == null && this.searchCriteria.pricePerNightMax == null && this.searchCriteria.numberOfRoomsMin == null && this.searchCriteria.numberOfRoomsMax == null){
				alert("No values");
			}
			else if(this.searchCriteria.startDate != null && this.searchCriteria.endDate == null || this.searchCriteria.startDate == null && this.searchCriteria.endDate != null ){
				alert("Both dates must be picked.");
			}
			else if(this.searchCriteria.startDate > this.searchCriteria.endDate ){
				alert("Invalid date values");
			}
			else{
				if(this.searchCriteria.pricePerNightMin == null)
					this.searchCriteria.pricePerNightMin = -1;
				if(this.searchCriteria.pricePerNightMax == null)
					this.searchCriteria.pricePerNightMax = -1;
				if(this.searchCriteria.numberOfRoomsMin == null)
					this.searchCriteria.numberOfRoomsMin = -1;
				if(this.searchCriteria.numberOfRoomsMax == null)
					this.searchCriteria.numberOfRoomsMax = -1;
				axios
					.get("/Project/rest/apartments/filterApartments",{params : {location : this.searchCriteria.location, numberOfGuests : this.searchCriteria.numberOfGuests, pricePerNightMin : this.searchCriteria.pricePerNightMin,pricePerNightMax : this.searchCriteria.pricePerNightMax , startDate : this.searchCriteria.startDate, endDate :this.searchCriteria.endDate ,numberOfRoomsMin: this.searchCriteria.numberOfRoomsMin , numberOfRoomsMax: this.searchCriteria.numberOfRoomsMax}})
					.then(response => {this.apartments = response.data;
					});
					
				if(this.searchCriteria.pricePerNightMin == -1)
					this.searchCriteria.pricePerNightMin = null;
				if(this.searchCriteria.pricePerNightMax == -1)
					this.searchCriteria.pricePerNightMax = null;
				if(this.searchCriteria.numberOfRoomsMin == -1)
					this.searchCriteria.numberOfRoomsMin = null;
				if(this.searchCriteria.numberOfRoomsMax == -1)
					this.searchCriteria.numberOfRoomsMax = null;
			}
    	},	    	
		createReservation: function(reservationInfo){
			if(this.searchCriteria.startDate != null && this.searchCriteria.endDate != null){
		var reservationInfo = {user:this.reservationInfo.user,apartment:this.reservationInfo.apartment,startDate:this.reservationInfo.startDate,endDate:this.reservationInfo.endDate,reservationMessage:this.reservationInfo.reservationMessage};
				
				axios
					.post("/Project/rest/apartments/bookApartment",reservationInfo)
					.then(response =>{} );
				axios
					.post("/Project/rest/users/bookApartment",reservationInfo)
					.then(response => {alert("Reservation sucessfull");
					});
					
				axios
					.get("/Project/rest/apartments/getAllApartments")
					.then(response => {this.apartments = response.data;
					});
			    axios
				    .get("/Project/rest/users/currentUser")
				    .then(response => {this.currentUser = response.data;
				   });
	
				this.searchCriteria.location = null;
				this.searchCriteria.numberOfGuests = null;
				this.searchCriteria.numberOfRoomsMin = null;
				this.searchCriteria.numberOfRoomsMax = null;
				this.searchCriteria.pricePerNightMin = null;
				this.searchCriteria.pricePerNightMax = null;
				this.searchCriteria.startDate = null;
				this.searchCriteria.endDate = null;
				}
			else{
				alert("Use date filter first");
			}
					
    	},
		resetFilters : function(){

				this.searchCriteria.location = null;
				this.searchCriteria.numberOfGuests = null;
				this.searchCriteria.numberOfRoomsMin = null;
				this.searchCriteria.numberOfRoomsMax = null;
				this.searchCriteria.pricePerNightMin = null;
				this.searchCriteria.pricePerNightMax = null;
				this.searchCriteria.startDate = null;
				this.searchCriteria.endDate = null;
				
				axios
					.get("/Project/rest/apartments/getAllApartments")
					.then(response => {this.apartments = response.data;
					});
			
}
	},

});