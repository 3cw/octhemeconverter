<?php if ($error_warning) { ?>
<div class="warning"><?php echo $error_warning; ?></div>
<?php } ?>
<?php if ($shipping_methods) { ?>
<p><?php echo $text_shipping_method; ?></p>
<table class="radio">
  <?php foreach ($shipping_methods as $shipping_method) { ?>
  <tr>
    <td colspan="3"><b><?php echo $shipping_method['title']; ?></b></td>
  </tr>
  <?php if (!$shipping_method['error']) { ?>
  <?php foreach ($shipping_method['quote'] as $quote) { ?>
  <tr class="highlight">
    <td><?php if ($quote['code'] == $code || !$code) { ?>
      <?php $code = $quote['code']; ?>
      <input type="radio" name="shipping_method" value="<?php echo $quote['code']; ?>" id="<?php echo $quote['code']; ?>" checked="checked" />
      <?php } else { ?>
      <input type="radio" name="shipping_method" value="<?php echo $quote['code']; ?>" id="<?php echo $quote['code']; ?>" />
      <?php } ?></td>
    <td><label for="<?php echo $quote['code']; ?>"><?php echo $quote['title']; ?></label></td>
    <td style="text-align: right;"><label for="<?php echo $quote['code']; ?>"><?php echo $quote['text']; ?></label></td>
  </tr>
  <?php } ?>
  <?php } else { ?>
  <tr>
    <td colspan="3"><div class="error"><?php echo $shipping_method['error']; ?></div></td>
  </tr>
  <?php } ?>
  <?php } ?>
</table>
<br />
<?php } ?>
<!-- 3CW LOGIC -->
<div id="shipping_additional_services" />
<!-- 3CW LOGIC END-->
<b><?php echo $text_comments; ?></b>
<textarea name="comment" rows="8" style="width: 98%;"><?php echo $comment; ?></textarea>
<br />
<br />
<div class="buttons">
  <div class="right">
    <input type="button" value="<?php echo $button_continue; ?>" id="button-shipping-method" class="button" />
  </div>
</div>

<!-- 3CW LOGIC -->
<script>
<?php bg_log("Code: $code"); ?>
<?php if ($code) { ?>
loadAdditionalServices(true);
<?php } ?>

$('input[name=\'shipping_method\']').bind( "change", function() {
	console.log( "Shipping methods selected: " + this.value );
	if (this.value == '') return;
	var shipping_method=this.value;

	loadAdditionalServices(true);
});

$('#button-shipping-method').live('click', function() {
	loadAdditionalServices(false);
});
function loadAdditionalServices(animate){
	if (animate)
		$('#shipping_additional_services').hide(250);
	$.ajax({
		url: 'index.php?route=checkout/shipping_method/validate',
		data: $('#shipping-method input[type=\'radio\']:checked, input[name=\'shipping_additional_service\']:checked, #shipping-additional-service .shipping_additional_field'), 
		dataType: 'json',
		beforeSend: function() {
			$(this).after('<span class="wait">&nbsp;<img src="catalog/view/theme/default/image/loading.gif" alt="" /></span>');
		},
		complete: function() {
			$('.wait').remove();
			if (animate)
			    $('#shipping_additional_services').show(500);
		},			
		success: function(json) {
			var html=json['html'];
			$('#shipping_additional_services').html(html);
			$('input[name=\'shipping_additional_service\']').bind( "click", function() {
				loadAdditionalServices(false);
			});
			
			$('#shipping-additional-service #button-refresh').click(function(){
				loadAdditionalServices(false);
			});
		},
		error: function(xhr, ajaxOptions, thrownError) {
			alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
		}
	
	});
}
</script>