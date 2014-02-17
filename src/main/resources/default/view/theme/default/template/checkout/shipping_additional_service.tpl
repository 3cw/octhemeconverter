<!-- 3CW LOGIC -->
<div id="shipping-additional-service">
<?php if ($error_warning) { ?>
<div class="warning"><?php echo $error_warning; ?></div>
<?php } ?>
<?php if ($shipping_additional_services) { ?>
<p><?php echo $text_shipping_additional_service; ?></p>
<table class="radio">
  <?php foreach ($shipping_additional_services as $shipping_additional_service) { ?>
  <?php if (!$shipping_additional_service['error']) { ?>
  <tr class="highlight">
    <td>
    	
    	<?php if ($shipping_additional_service['optional'] && $shipping_additional_service['selected']) { ?>
     		<input type="checkbox" name="shipping_additional_service" value="<?php echo $shipping_additional_service['id']; ?>" id="<?php echo $shipping_additional_service['code']; ?>" checked="checked" />
        <?php } else if ($shipping_additional_service['optional'] && !$shipping_additional_service['selected']){ ?>
      		<input type="checkbox" name="shipping_additional_service" value="<?php echo $shipping_additional_service['id']; ?>" id="<?php echo $shipping_additional_service['code']; ?>" />
        <?php } else {?>
        <?php } ?>
    </td>
    <td><label for="<?php echo $quote['code']; ?>"><?php echo $shipping_additional_service['name']; ?></label><p><?php echo $shipping_additional_service['description']; ?></p></td>
    <td style="text-align: right;"><label for="<?php echo $quote['code']; ?>"><?php echo $shipping_additional_service['amount']; ?></label></td>
  </tr>
  <?php } else { ?>
  <tr>
    <td colspan="3"><div class="error"><?php echo $shipping_additional_service['error']; ?></div></td>
  </tr>
  <?php } ?>
  <?php } ?>
</table>
<br />
<?php } ?>

<?php if ($shipping_additional_fields) { ?>
<p><?php echo $text_shipping_additional_fields; ?></p>
<table class="radio">
  <?php foreach ($shipping_additional_fields as $shipping_additional_field) { ?>
  <tr class="highlight">
    <td>
     		<input type="text" name="<?php echo $shipping_additional_field['code']; ?>" class="shipping_additional_field" value="<?php echo $shipping_additional_field['value']; ?>" id="<?php echo $shipping_additional_field['code']; ?>" />
    </td>
    <td><label for="<?php echo $quote['code']; ?>"><?php echo $shipping_additional_field['name']; ?></label></td>
    <td style="text-align: right;"><label for="<?php echo $quote['code']; ?>"><?php echo $shipping_additional_field['description']; ?></label></td>
  </tr>
  <?php if ($shipping_additional_field['error']) { ?>
    <td>&nbsp;</td><td colspan="2"><div class="error"><?php echo $shipping_additional_field['error']; ?></div></td>
  </tr>
  <?php } ?>
  <?php } ?>
</table>
<br />
<?php } ?>

<?php if ($errors) { ?>
<table class="radio">
  <?php foreach ($errors as $error) { ?>
  <tr class="highlight">
    <td>
    	<?php echo $error; ?>
	</td>
  </tr>
  <?php } ?>
</table>
<?php } ?>

<?php if ($shipping_additional_fields || $shipping_additional_services) { ?>
<div class="buttons">
	<div class="left">
  		<?php echo $text_shipping_costs_total; ?>
  		<div id="shipping_costs_total"><?php echo $shipping_costs_total; ?></div>
  	</div>
	  <div class="right">
	    <input type="button" value="<?php echo $button_refresh; ?>" id="button-refresh" class="button" />
	  </div>
	</div>
</div>
<?php } ?>