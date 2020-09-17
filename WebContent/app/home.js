var home = new Vue({
	el : '#home',
	data:{
		mode:'',
		loginInfo:{},
		currentUser:{},
		currentUsername:'',
		apartments : {} ,
		labelOldPassword : ""
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
		
			
		
	},
	watch: {
		currentUsername(newUsername) {
	      localStorage.currentUsername = newUsername;
	    }
	},
	methods:{
    	logout : function(){
    		this.currentUsername = '';
    		this.mode = 'BROWSE';
    		axios
    		.post("/Project/rest/users/logout")
    		router.back();
    	},
    	getLoggedUser : function(){
    		axios
    		.get("/Project/rest/users/currentUser")
    		.then(response => (this.currentUser = response.data));
    	}
	}
});

var register = new Vue({
	el:'#registerModal',
	data:{
		newUser:{},
		usernameLabel : "",
		passwordLabel : "",
		confirmLabel : "",
		firstNameLabel : "",
		lastNameLabel : "",
		roleLabel : "",
		genderLabel : ""
	},
	mounted () {
		this.newUser.role = "GUEST";
    },
	methods:{
		register : function(user){
			if(user.username == "" || user.username == null){
				this.usernameLabel = "This field must be filled";
			}
			if(user.password == "" || user.password == null){
				this.passwordLabel = "This field must be filled";
			}
			if(user.passwordConfirm == "" || user.passwordConfirm == null){
				this.confirmLabel = "This field must be filled";
			}
			if(user.firstName == "" || user.firstName == null){
				this.firstNameLabel = "This field must be filled";
			}
			if(user.lastName == "" || user.lastName == null){
				this.lastNameLabel = "This field must be filled";
			}
			if(user.gender == "" || user.gender == null ){
				this.genderLabel = "This field must be filled";
			}
			if(user.role == "" || user.role == null){
				this.roleLabel = "This field must be filled";
			}
			
			if(user.username != "" && user.username != undefined && user.username != null && user.password != "" && user.password != undefined && user.password != null && user.passwordConfirm != "" && user.passwordConfirm != undefined && user.passwordConfirm != null && user.firstName != "" &&  user.firstName != undefined && user.firstName != null && user.lastName != "" && user.lastName != undefined && user.lastName != null && user.gender != "" && user.gender != undefined && user.gender != null){
				if(user.password != user.passwordConfirm){
					this.confirmLabel = "Passwords dont match";
				}else{
				var u = {username : user.username, password : user.password, firstName : user.firstName, lastName : user.lastName, role:user.role, gender:user.gender};
				axios
		          .post("/Project/rest/users/register", u)
		          .then(response => { 
		        	  if(response.data=="OK"){
		        		  (alert("User " + u.firstName + " " + u.lastName + " is successfuly registered."));
									this.usernameLabel = "";
									this.passwordLabel = "";
									this.confirmLabel = "";
									this.firstNameLabel = "";
									this.lastNameLabel = "";
									this.roleLabel = "";
									this.genderLabel = "";
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
		labelUsernameLogin : '',
		labelPasswordLogin : '',
	},
	mounted () {
		this.loginInfo.username = '';
		this.loginInfo.password = '';
		this.logedUser = {};
    },
	methods:{
		login : function(loginInfo){
			if(this.loginInfo.username == '' && this.loginInfo.password != ''){
				this.labelUsernameLogin = "Username can't be empty";
			}
			else if(this.loginInfo.password == '' && this.loginInfo.username != '' ){
				this.labelPasswordLogin = "Password can't be empty";
			}
			else if(this.loginInfo.password == '' && this.loginInfo.username == '' ){
				this.labelUsernameLogin = "Username can't be empty";
				this.labelPasswordLogin = "Password can't be empty";
			}
			else{			
				axios
		          .get("/Project/rest/users/login", {params: {username:loginInfo.username, password : loginInfo.password}})
		          .then(response => {
					accountModal.logedUser = response.data;
					accountModal.updatedUser = response.data;
					accountModal.updatedUser.oldPassword = accountModal.logedUser.password;
					this.logedUser = response.data; 					
					if(this.logedUser != ""){
						this.labelUsernameLogin = "";
						this.labelPasswordLogin = "";
						home.currentUsername = this.logedUser.username;
						home.currentUser.firstName = this.logedUser.firstName;
						home.currentUser.lastName = this.logedUser.lastName;
						this.$root.$emit('userFromParent', response.data);
						home.mode = "LOGIN";
						accountModal.updatedUser.password = null;
						$('#loginModal').modal('hide')
						if(this.logedUser.role == "HOST")
							router.push("host")
						else if(this.logedUser.role == "GUEST")
							router.push("guest")
						else if(this.logedUser.role == "ADMINISTRATOR")
							router.push("admin")
						}
					else{
						this.labelUsernameLogin = "Username or password incorrect";
						this.labelPasswordLogin = "";
						} })

		}
		}
		}
});

var accountModal = new Vue({
	el:'#accountModal',
	data:{
		logedUser : {},
		updatedUser : {},
		labelOldPassword : "",
		labelOldPasswordConfirm : ""
	},
	mounted () {
			
		axios	
			.get("/Project/rest/users/currentUser")
			.then(response => {this.logedUser = response.data;
			this.updatedUser.username = this.logedUser.username;
			this.updatedUser.oldPassword = this.logedUser.password;
			this.updatedUser.username = this.logedUser.username;
			this.updatedUser.firstName = this.logedUser.firstName;
			this.updatedUser.lastName = this.logedUser.lastName;
			this.updatedUser.oldPasswordX = "";
			});

			

		
    },
	methods:{
		updateAccount : function(updatedUser){	
			if(updatedUser.oldPassword == updatedUser.oldPasswordX){	
				axios
		          .get("/Project/rest/users/updateAccount",{params:{username : updatedUser.username, oldPassword : updatedUser.oldPassword,
															password : updatedUser.password , confirmPassword : updatedUser.confirmPassword ,
															firstName : updatedUser.firstName , lastName : updatedUser.lastName ,oldPasswordX : updatedUser.oldPasswordX}})
		          .then(response => {
						if(response.data){
						axios	
							.get("/Project/rest/users/currentUser")
							.then(response => {this.logedUser = response.data;
								this.updatedUser.username = this.logedUser.username;
								this.updatedUser.oldPassword = this.logedUser.password;
								this.updatedUser.username = this.logedUser.username;
								this.updatedUser.firstName = this.logedUser.firstName;
								this.updatedUser.lastName = this.logedUser.lastName;
								this.labelOldPassword = "";
								this.labelOldPasswordConfirm = "";
								this.updatedUser.oldPasswordX = "";	
												
												
												
							});
							alert("Sucessfuly updated");
							$('#accountModal').modal('hide')
						}
						else
							this.labelOldPasswordConfirm = "Passwords are not matching";
							})

		}
		else if(updatedUser.oldPasswordX == ""){
			this.labelOldPassword = "This field cant remain empty";
		}
		}
		}
});

const Browse = { template: '<browse></browse>' }
const Guest = { template: '<guest></guest>' }
const Admin = { template: '<admin></admin>' }
const Host = { template: '<host></host>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { 
	    	path: '/', 
	    	name : 'home', 
	    	component: Browse,
	    	beforeEnter : (to, from, next) => {
	    		axios
	    		.get("/Project/rest/users/currentUser")
	    		.then(response => {
	    			if(response.data.role == undefined){
	    				next();
	    			}else{
	    				next(false);
	    			}
	    		})
	    	}
	    },
	    { 
	    	path: '/guest', 
	    	name : 'guest', 
	    	component: Guest,
	    	beforeEnter : (to, from, next) => {
	    		axios
	    		.get("/Project/rest/users/currentUser")
	    		.then(response => {
	    			if(response.data.role === 'GUEST'){
	    				next();
	    			}else{
	    				next(false);
	    			}
	    		})
	    	}
	    	
	    },
	    {
	    	path: '/admin', 
	    	name : 'admin', 
	    	component: Admin,
	    	beforeEnter : (to, from, next) => {
	    		axios
	    		.get("/Project/rest/users/currentUser")
	    		.then(response => {
	    			if(response.data.role === 'ADMINISTRATOR'){
	    				next();
	    			}else{
	    				next(false);
	    			}
	    		})
	    	}
	    	
	    },
	    { 
	    	path: '/host',  
	    	name : 'host', 
	    	component: Host,
	    	beforeEnter : (to, from, next) => {
	    		axios
	    		.get("/Project/rest/users/currentUser")
	    		.then(response => {
	    			if(response.data.role === 'HOST'){
	    				next();
	    			}else{
	    				next(false);
	    			}
	    		})
	    	}
	    }
	  ]
});

var app = new Vue({
	router,
	el: '#routerMode'
});

