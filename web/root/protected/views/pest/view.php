<?php
/* @var $this PestController */
/* @var $model Pest */

$this->breadcrumbs=array(
	'Pests'=>array('index'),
	$model->Name,
);

$this->menu=array(
	array('label'=>'List Pest', 'url'=>array('index')),
	array('label'=>'Create Pest', 'url'=>array('create')),
	array('label'=>'Update Pest', 'url'=>array('update', 'id'=>$model->Id)),
);
?>

<h1>View Pest "<?php echo $model->Name; ?>"</h1>

<?php $this->widget('bootstrap.widgets.TbDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'Name',
		'Description',
	),
)); ?>

<?php echo $this->renderPartial('//site/_imageManager', array('model'=>$model, 'canDelete' => false)); ?>
