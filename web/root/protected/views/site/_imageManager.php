<?php if(isset($model)):?>
	<?php foreach($model->images as $image): ?>
		<img src="<?php echo Yii::app()->params['siteDomain'].Yii::app()->params['imagePath'].$image->photo->Name;?>" width="150px" height="70px" />
	<?php endforeach; ?>
<?php endif; ?>