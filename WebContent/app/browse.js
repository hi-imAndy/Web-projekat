Vue.component("browse", {
	data: function(){
		return{
			currentUser: {}
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
	</div>
	`,
	mounted(){
		
	}
});