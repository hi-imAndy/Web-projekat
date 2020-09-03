
Vue.component("browse", {
	data: function(){
		return{
			currentUser: {},
			apartments:{},
			selected:{}
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
			</div>
			<div class = "row">
				<div class="col">
					<select class="browser-default custom-select">
						<option value="All">All</option>
						<option value="Novi Sad">Novi Sad</option>
						<option value="Beograd">Beograd</option>	
				  	</select></div>
				<div class="col"><input type="date" id="picker" class="form-control"></div>
				<div class="col"><input type="date" id="picker" class="form-control"></div>
				<div class="col"><b>0 </b><input type="range" id="myRange" class="slider"  min="0" value="5" max="10" style="vertical-align:middle;"><b> 5000</b></div>
				<div class="col"><input type = "number" value="1" min="1" max="25" step="1" class = "form-control"></div>
			</div>
		</div>
		
			<div class="container">
				<table class="table">
				  <div class="row">
				    <div class="col-12">
						<table class="table table-image">
						  <thead>
						    <tr>
						      <th scope="col"></th>
						      <th scope="col">Appartment ID</th>
						      <th scope="col">Host</th>
						      <th scope="col">Location</th>
						      <th scope="col">Status</th>
						    </tr>
						  </thead>
						  <tbody>
									<tr v-for="u in apartments" v-on:click="selectApartment(u)" v-bind:class = "{selected : selected.id === u.id}" data-toggle="modal" data-target="#apartmentInfo">
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
							      <div class="modal-footer">
											<div class="row justify-content-md-center">
											<div class="col-md-auto">
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
		axios
			.get("/Project/rest/apartments/getAllApartments")
			.then(response => {this.apartments = response.data;
			});
	},
	methods : {
		selectApartment : function(apartment){
			this.selected = apartment;
    	}
	},
});



