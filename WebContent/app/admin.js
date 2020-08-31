Vue.component("admin", {
	data: function () {
		    return {
		    	users:{},
		    	searchUsername:"",
				searchRole:"",
				searchGender:"",
				usernameChecked:false,
				roleChecked:false,
				genderChecked:false
		    }
	},
	template: ` 
	<div>
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
					<button type="button" class="btn btn-secondary" v-on:click="search">Search</button>
				</div>
		    </div>
			<div class = "row justify-content-md-center">
				<div class = "col-md-auto">
					<h3>List of all users</h3>
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
	</div>
	`,
	mounted(){
		axios
		.get("/Project/rest/users/getAllUsers")
		.then(response => (this.users = response.data));

	},
	methods : {
		search : function(){
    		if(this.usernameChecked === true || this.roleChecked === true || this.genderChecked == true){
    			axios
        		.get("/Project/rest/users/searchUsers", {params: {username : this.searchUsername, role : this.searchRole, gender: this.searchGender, u : this.usernameChecked, r: this.roleChecked, g: this.genderChecked}})
        		.then(response => (this.users = response.data))
    		}
    	}
	},
});