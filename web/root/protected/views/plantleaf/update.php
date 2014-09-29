<?php
/* @var $this PlantLeafController */
/* @var $model PlantLeaf */

$this->breadcrumbs=array(
	'Plant / Leaf Symptoms'=>array('index'),
	$model->Name=>array('view','id'=>$model->Id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Plant / Leaf Symptoms', 'url'=>array('index')),
	array('label'=>'Create Plant / Leaf Symptoms', 'url'=>array('create')),
	array('label'=>'View Plant / Leaf Symptoms', 'url'=>array('view', 'id'=>$model->Id)),
);
?>

<h1>Update Plant / Leaf Symptom "<?php echo $model->Name; ?>"</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>