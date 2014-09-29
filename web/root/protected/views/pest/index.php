<?php
/* @var $this PestController */
/* @var $model Pest */

$this->breadcrumbs=array(
	'Pests'=>array('index'),
	'Manage',
);

$this->menu=array(
	array('label'=>'List Pest', 'url'=>array('index')),
	array('label'=>'Create Pest', 'url'=>array('create')),
);

Yii::app()->clientScript->registerScript('search', "
$('.search-button').click(function(){
	$('.search-form').toggle();
	return false;
});
$('.search-form form').submit(function(){
	$('#pest-grid').yiiGridView('update', {
		data: $(this).serialize()
	});
	return false;
});
");
?>

<h1>Manage Pests</h1>

<?php echo CHtml::link('Advanced Search','#',array('class'=>'search-button')); ?>
<div class="search-form" style="display:none">
<?php $this->renderPartial('_search',array(
	'model'=>$model,
)); ?>
</div><!-- search-form -->

<?php $this->widget('bootstrap.widgets.TbGridView', array(
	'id'=>'pest-grid',
	'dataProvider'=>$model->search(),
	'filter'=>$model,
	'columns'=>array(
		'Name',
		'Description',
		array(
			'class'=>'CButtonColumn',
		),
	),
)); ?>
