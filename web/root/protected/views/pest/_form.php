<?php
/* @var $this PestController */
/* @var $model Pest */
/* @var $form CActiveForm */
?>

<div class="form">

<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'pest-form',
	'enableAjaxValidation'=>false,
	'htmlOptions' => array('enctype' => 'multipart/form-data'),
)); ?>

	<p class="note">Fields with <span class="required">*</span> are required.</p>

	<?php echo $form->errorSummary($model); ?>

	<div class="row">
		<?php echo $form->labelEx($model,'Name'); ?>
		<?php echo $form->textField($model,'Name',array('size'=>50,'maxlength'=>50)); ?>
		<?php echo $form->error($model,'Name'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'Description'); ?>
		<?php echo $form->textArea($model,'Description',array('rows'=>6, 'cols'=>50)); ?>
		<?php echo $form->error($model,'Description'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'image'); ?>
		<p class="note">You can upload only one image per submission.</p>
		<?php echo $form->fileField($model,'image'); ?>
		<?php echo $form->error($model,'image'); ?>
	</div>

	<div id="tutorial-list" class="row">
	<label for="Pest_Tutorials" class="required">Link this Pest with a tutorial</label>
		<div class="checkbox-list">		
		<?php
			$selected_keys = array();


			if(!$model->isNewRecord)
			{
				/* Create an array of html options - which tutorials were selected. */
				$selected_keys = array_keys(CHtml::listData($model->pestTutorials, 'TutorialId' , 'TutorialId'));
			}


			echo CHtml::checkBoxList(get_class($model).'[Tutorials][]', $selected_keys, $tutorialList); ?>
		</div>
	</div>
	
	<div class="row buttons">
		<?php echo CHtml::submitButton($model->isNewRecord ? 'Create' : 'Save'); ?>
	</div>

<?php $this->endWidget(); ?>

	<?php if(!$model->isNewRecord): ?>
	<?php echo $this->renderPartial('//site/_imageManager', array('model'=>$model, 'canDelete' => true)); ?>
	<?php endif; ?>

</div><!-- form -->