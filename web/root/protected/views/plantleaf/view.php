<?php
/* @var $this PlantLeafController */
/* @var $model PlantLeaf */

$this->breadcrumbs=array(
	'Plant / Leaf Symptoms'=>array('index'),
	$model->Name,
);

$this->menu=array(
	array('label'=>'List Plant / Leaf Symptoms', 'url'=>array('index')),
	array('label'=>'Create Plant / Leaf Symptoms', 'url'=>array('create')),
	array('label'=>'Update Plant / Leaf Symptoms', 'url'=>array('update', 'id'=>$model->Id)),
	array('label'=>'Delete Plant / Leaf Symptoms', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->Id),'confirm'=>'Are you sure you want to delete this item?')),
);
?>

<h1>View Plant / Leaf Symptom "<?php echo $model->Name; ?>"</h1>

<?php $this->widget('bootstrap.widgets.TbDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'Name',
		'Description',
	),
)); ?>

<?php echo $this->renderPartial('//site/_imageManager', array('model'=>$model, 'canDelete' => false)); ?>
