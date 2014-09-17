<?php
/* @var $this PestController */
/* @var $model Pest */

$this->breadcrumbs=array(
	'Pests'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Pest', 'url'=>array('index')),
	array('label'=>'Manage Pest', 'url'=>array('admin')),
);
?>

<h1>Create Pest</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>