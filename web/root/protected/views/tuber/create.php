<?php
/* @var $this TuberController */
/* @var $model Tuber */

$this->breadcrumbs=array(
	'Tubers'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Tuber', 'url'=>array('index')),
	array('label'=>'Manage Tuber', 'url'=>array('admin')),
);
?>

<h1>Create Tuber</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>