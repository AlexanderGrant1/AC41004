<?php
	$cs=Yii::app()->clientScript;

	$cs->registerCssFile(Yii::app()->baseUrl.'/css/lightbox.css');
	$cs->registerScriptFile(Yii::app()->baseUrl.'/js/lightbox.min.js', CClientScript::POS_END);
	
	if($canDelete)
	{
		$cs->registerScriptFile(Yii::app()->baseUrl.'/js/imageDel.js', CClientScript::POS_END);
	}
	Yii::app()->clientScript->registerCoreScript('jquery'); 
?>


<?php if(isset($model->images)):?>
	<div id="image-manager">
	<?php foreach($model->images as $image): ?>
		<div id="<?php echo get_class($model); ?>-<?php echo $image->Id;?>" class="entry<?php if($canDelete) {echo ' delete';} ?>">
			<span class="pic">
				<a href="<?php echo Yii::app()->params['siteDomain'].Yii::app()->params['imagePath'].$image->photo->Name;?>" data-lightbox="example-set" ><img src="<?php echo Yii::app()->params['siteDomain'].Yii::app()->params['imagePath'].$image->photo->Name;?>" /></a>
			</span>
			<?php if($canDelete): ?>
			<span class="remove">
				Remove
			</span>
			<?php endif; ?>
		</div>
	<?php endforeach; ?>
	</div>

	<script type="text/javascript">
        $(document).ready(function () {
        	$('#image-manager').imageDel('<?php echo Yii::app()->params['siteDomain'];?>');
        });
    </script>
<?php endif; ?>