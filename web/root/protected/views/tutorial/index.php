<?php
/* @var $this TutorialController */
/* @var $model Tutorial */

$this->breadcrumbs=array(
	'Tutorials'=>array('index'),
	'Manage',
);

$this->menu=array(
	array('label'=>'List Tutorial', 'url'=>array('index')),
	array('label'=>'Create Tutorial', 'url'=>array('create')),
);

Yii::app()->clientScript->registerScript('search', "
$('.search-button').click(function(){
	$('.search-form').toggle();
	return false;
});
$('.search-form form').submit(function(){
	$('#tutorial-grid').yiiGridView('update', {
		data: $(this).serialize()
	});
	return false;
});
");
?>

<h1>Manage Tutorials</h1>

<?php echo CHtml::link('Advanced Search','#',array('class'=>'search-button')); ?>
<div class="search-form" style="display:none">
<?php $this->renderPartial('_search',array(
	'model'=>$model,
)); ?>
</div><!-- search-form -->

<?php $this->widget('bootstrap.widgets.TbGridView', array(
	'id'=>'tutorial-grid',
	'dataProvider'=>$model->search(),
	'filter'=>$model,
	'columns'=>array(
		'Name',
		'Description',
		'VideoName',
		array(
			'class'=>'CButtonColumn',
		),
	),
)); ?>
