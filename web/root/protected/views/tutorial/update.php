<?php
/* @var $this TutorialController */
/* @var $model Tutorial */

$this->breadcrumbs=array(
	'Tutorials'=>array('index'),
	$model->Name=>array('view','id'=>$model->Id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Tutorial', 'url'=>array('index')),
	array('label'=>'Create Tutorial', 'url'=>array('create')),
	array('label'=>'View Tutorial', 'url'=>array('view', 'id'=>$model->Id)),
);
?>

<h1>Update Tutorial <?php echo $model->Id; ?></h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>