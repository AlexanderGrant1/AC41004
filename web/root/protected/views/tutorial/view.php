<?php
/* @var $this TutorialController */
/* @var $model Tutorial */

$this->breadcrumbs=array(
	'Tutorials'=>array('index'),
	$model->Name,
);

$this->menu=array(
	array('label'=>'List Tutorial', 'url'=>array('index')),
	array('label'=>'Create Tutorial', 'url'=>array('create')),
	array('label'=>'Update Tutorial', 'url'=>array('update', 'id'=>$model->Id)),
	array('label'=>'Delete Tutorial', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->Id),'confirm'=>'Are you sure you want to delete this item?')),
);
?>

<h1>View Tutorial #<?php echo $model->Id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'Id',
		'Name',
		'Description',
		'VideoName',
	),
)); ?>
