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
									<tr v-for="u in apartments" v-on:click="selectApartment(u)" v-bind:class = "{selected : selected.id === u.id}">
									<img v-bind:src="u.pictures[1]" class="img-fluid img-thumbnail " width="250" height="100">
									<td>{{u.id }}</td>
									<td>{{u.id }}</td>
									<td>{{u.id}}</td>
									<td>{{u.id }}</td>
							    </tr>
						  </tbody>
						</table>   
				    </div>
				  </div>
				</table>
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