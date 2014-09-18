<?php
/* @var $this TuberController */
/* @var $model Tuber */

$this->breadcrumbs=array(
	'Tubers'=>array('index'),
	$model->Name=>array('view','id'=>$model->Id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Tuber', 'url'=>array('index')),
	array('label'=>'Create Tuber', 'url'=>array('create')),
	array('label'=>'View Tuber', 'url'=>array('view', 'id'=>$model->Id)),
	array('label'=>'Manage Tuber', 'url'=>array('admin')),
);
?>

<h1>Update Tuber <?php echo $model->Id; ?></h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>