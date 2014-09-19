<?php
	$cs=Yii::app()->clientScript;

	$cs->registerCssFile(Yii::app()->baseUrl.'/css/lightbox.css');
	$cs->registerScriptFile(Yii::app()->baseUrl.'/js/lightbox.min.js', CClientScript::POS_END);
	$cs->registerScriptFile(Yii::app()->baseUrl.'/js/imageDel.js', CClientScript::POS_END);

	Yii::app()->clientScript->registerCoreScript('jquery'); 
?>


<?php if(isset($model->images)):?>
	<div id="image-manager">
	<?php foreach($model->images as $image): ?>
		<div id="<?php echo get_class($model); ?>-<?php echo $image->Id;?>" class="entry">
			<span class="pic">
				<a href="<?php echo Yii::app()->params['siteDomain'].Yii::app()->params['imagePath'].$image->photo->Name;?>" data-lightbox="example-set" ><img src="<?php echo Yii::app()->params['siteDomain'].Yii::app()->params['imagePath'].$image->photo->Name;?>" width="150px" height="70px" /></a>
			</span>
			<span class="remove">
				Remove
			</span>
		</div>
	<?php endforeach; ?>
	</div>

	<script type="text/javascript">
        $(document).ready(function () {
        	$('#image-manager').imageDel('/potato/');
        });
    </script>
<?php endif; ?>