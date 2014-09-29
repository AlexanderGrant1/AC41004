<?php
/* @var $this PlantLeafController */
/* @var $model PlantLeaf */

$this->breadcrumbs=array(
	'Plant / Leaf Symptoms'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Plant / Leaf Symptoms', 'url'=>array('index')),
);
?>

<h1>Create Plant / Leaf Symptom</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model,'tutorialList' => $tutorialList)); ?>