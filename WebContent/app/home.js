var home = new Vue({
	el : '#home',
	data:{
		users:null,
		mode:"BROWSE",
		searchUsername:"",
		searchRole:"",
		searchGender:"",
		loginInfo:{},
		currentUser:{},
		usernameChecked:false,
		roleChecked:false,
		genderChecked:false
	},
	mounted(){
		axios
		.get("/Project/rest/users/getAllUsers")
		.then(response => (this.users = response.data))
	},
	methods:{
		getAllUsers: function(){
    		axios
    		.get("/Project/rest/users/getAllUsers")
    		.then(response => (this.users = response.data))
    	},
    	search : function(){
    		if(this.usernameChecked === true || this.roleChecked === true || this.genderChecked == true){
    			axios
        		.get("/Project/rest/users/searchUsers", {params: {username : this.searchUsername, role : this.searchRole, gender: this.searchGender, u : this.usernameChecked, r: this.roleChecked, g: this.genderChecked}})
        		.then(response => (this.users = response.data))
    		}
    	},
    	login : function(){
    		this.mode = 'logged';
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
		logedUser : null,
		mode : "BROWSE"
	},
	mounted () {
		this.loginInfo.username = null;
		this.logedUser = null;
		this.mode = "BROWSE"
		home.mode = "BROWSE";
    },
	methods:{
		login : function(loginInfo){				
				axios
		          .get("/Project/rest/users/login", {params: {username : loginInfo.username, password : loginInfo.password}})
		          .then(response => {
					this.logedUser = response.data; 					
					if(this.logedUser != ""){
						home.currentUser.firstName = this.logedUser.firstName;
						home.currentUser.lastName = this.logedUser.lastName;
						this.mode = "LOGIN";
						home.mode = "LOGIN";
						$('#loginModal').modal('hide')
      					
						}
					else{
						alert("Username or password are incorrect");
						} })

		}
		}
});


