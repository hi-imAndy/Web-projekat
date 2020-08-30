var home = new Vue({
	el : '#home',
	data:{
		users:{},
		amenities:{},
		mode:'',
		searchUsername:"",
		searchRole:"",
		searchGender:"",
		newApartment:{},
		loginInfo:{},
		currentUser:{},
		currentUsername:'',
		usernameChecked:false,
		roleChecked:false,
		genderChecked:false
	},
	mounted(){
		if(localStorage.currentUsername){
			this.currentUsername = localStorage.currentUsername;
			this.mode = "LOGIN";
			axios
    		.get("/Project/rest/users/currentUser")
    		.then(response => (this.currentUser = response.data));
		}else{
			this.mode = "BROWSE";
		}

		axios
		.get("/Project/rest/users/getAllUsers")
		.then(response => (this.users = response.data));
		
		axios
		.get("/Project/rest/amenities/getAllAmenities")
		.then(response => (this.amenities = response.data));
		
	},
	watch: {
		currentUsername(newUsername) {
	      localStorage.currentUsername = newUsername;
	    }
	},
	methods:{
    	search : function(){
    		if(this.usernameChecked === true || this.roleChecked === true || this.genderChecked == true){
    			axios
        		.get("/Project/rest/users/searchUsers", {params: {username : this.searchUsername, role : this.searchRole, gender: this.searchGender, u : this.usernameChecked, r: this.roleChecked, g: this.genderChecked}})
        		.then(response => (this.users = response.data))
    		}
    	},
    	logout : function(){
    		this.currentUsername = '';
    		this.mode = 'BROWSE';
    		axios
    		.post("/Project/rest/users/logout")
    	},
    	getLoggedUser : function(){
    		axios
    		.get("/Project/rest/users/currentUser")
    		.then(response => (this.currentUser = response.data));
    	},
    	updateImages : function(event){
    		var fileNames = [];
    		var files = event.target.files;
    		for(var i = 0, f; f = files[i]; i++){
    			fileNames.push(f.name);
    		}
    		this.newApartment.pictures = fileNames;
    	}
	}
});

var register = new Vue({
	el:'#registerModal',
	data:{
		newUser:{}
	},
	mounted () {
		this.newUser.role = "GUEST";
    },
	methods:{
		register : function(user){
			if(user.username != "" && user.username != undefined && user.username != null && user.password != "" && user.password != undefined && user.password != null && user.passwordConfirm != "" && user.passwordConfirm != undefined && user.passwordConfirm != null && user.firstName != "" &&  user.firstName != undefined && user.firstName != null && user.lastName != "" && user.lastName != undefined && user.lastName != null && user.gender != "" && user.gender != undefined && user.gender != null){
				if(user.password != user.passwordConfirm){
					alert("Passwords don't match!");
				}else{
				var u = {username : user.username, password : user.password, firstName : user.firstName, lastName : user.lastName, role:user.role, gender:user.gender};
				axios
		          .post("/Project/rest/users/register", u)
		          .then(response => { 
		        	  if(response.data=="OK"){
		        		  (alert("User " + u.firstName + " " + u.lastName + " is successfuly registered."));
							$('#registerModal').modal('hide')
						}
		        	  else
		        		  (alert("User with that username already exists!"));
		          })
				}
			}
		}
	}
});

var login = new Vue({
	el:'#loginModal',
	data:{
		loginInfo:{},
		logedUser : {},
	},
	mounted () {
		this.loginInfo.username = '';
		this.logedUser = {};
    },
	methods:{
		login : function(loginInfo){				
				axios
		          .get("/Project/rest/users/login", {params: {username:loginInfo.username, password : loginInfo.password}})
		          .then(response => {
					accountModal.logedUser = response.data;
					accountModal.updatedUser = response.data;
					accountModal.updatedUser.oldPassword = accountModal.logedUser.password;
					this.logedUser = response.data; 					
					if(this.logedUser != ""){
						home.currentUsername = this.logedUser.username;
						home.currentUser.firstName = this.logedUser.firstName;
						home.currentUser.lastName = this.logedUser.lastName;
						home.mode = "LOGIN";
						accountModal.updatedUser.password = null;
						$('#loginModal').modal('hide')
      					
						}
					else{
						alert("Username or password are incorrect");
						} })

		}
		}
});

var accountModal = new Vue({
	el:'#accountModal',
	data:{
		logedUser : {},
		updatedUser : {}
	},
	mounted () {
		
    },
	methods:{
		updateAccount : function(updatedUser){	
				
				axios
		          .get("/Project/rest/users/updateAccount",{params:{username : updatedUser.username, oldPassword : updatedUser.oldPassword,
															password : updatedUser.password , confirmPassword : updatedUser.confirmPassword ,
															firstName : updatedUser.firstName , lastName : updatedUser.lastName}})
		          .then(response => {
						if(response.data){
							alert("Sucessfuly updated");
							$('#accountModal').modal('hide')
						}
						else
							alert("Passwords are not matching");
							})

		}
		}
});

