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
		        	  if(response.data=="OK")
		        		  (alert("User " + u.firstName + " " + u.lastName + " is successfuly registered."));
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
	},
	methods:{
		login : function(){
			this.mode = 'logged';
		}
	}
});