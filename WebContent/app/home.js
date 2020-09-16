var home = new Vue({
	el : '#home',
	data:{
		mode:'',
		loginInfo:{},
		currentUser:{},
		currentUsername:'',
		apartments : {}
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

