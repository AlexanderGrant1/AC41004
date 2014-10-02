<?php

// uncomment the following to define a path alias
// Yii::setPathOfAlias('local','path/to/local-folder');

// This is the main Web application configuration. Any writable
// CWebApplication properties can be configured here.
return array(
	'basePath'=>dirname(__FILE__).DIRECTORY_SEPARATOR.'..',
	'name'=>'Potato',

	// preloading 'log' component
	'preload'=>array('log'),

	// path aliases
    'aliases' => array(
        'bootstrap' => realpath(__DIR__ . '/../extensions/bootstrap'), // change this if necessary
    ),

	// autoloading model and component classes
	'import'=>array(
		'application.models.*',
		'application.components.*',
        'bootstrap.behaviors.*',
        'bootstrap.helpers.*',
        'bootstrap.widgets.*',
	),

	'modules'=>array(
		// uncomment the following to enable the Gii tool
		'api',
		
		'gii'=>array(
			'class'=>'system.gii.GiiModule',
			'generatorPaths' => array('bootstrap.gii'),
			'password'=>'test',
			// If removed, Gii defaults to localhost only. Edit carefully to taste.
			'ipFilters'=>false,
		),
		
	),

	// application components
	'components'=>array(
		'phpThumb'=>array(
	    	'class'=>'application.extensions.EPhpThumb.EPhpThumb',
	    ),
		'user'=>array(
			// enable cookie-based authentication
			'allowAutoLogin'=>true,
		),
		// uncomment the following to enable URLs in path-format
		
		'urlManager'=>array(
			'urlFormat'=>'path',
			'showScriptName'=>false,
			'caseSensitive' => false,
			'rules'=>array(

				'gii' => 'gii/default/login',
				'gii/<controller:\w+>'=>'gii/<controller>',
				'gii/<controller:\w+>/<action:\w+>/'=>'gii/<controller>/<action>',
				
				//array('pest/delmedia', 'pattern' => '<controller:(pest)>/delpic/<id:\d+>', 'verb' => 'DELETE'),

				'admin/<controller:\w+>/<id:\d+>'=>'<controller>admin/view',
				'admin/<controller:\w+>/<action:\w+>/<id:\d+>'=>'<controller>admin/<action>',
				'admin/<controller:\w+>/<action:\w+>'=>'<controller>admin/<action>',

				'<controller:\w+>/<id:\d+>'=>'<controller>/view',
				'<controller:\w+>/<action:\w+>/<id:\d+>'=>'<controller>/<action>',
				'<controller:\w+>/<action:\w+>'=>'<controller>/<action>',
			),
		),
		
		/*'db'=>array(
			'connectionString' => 'sqlite:'.dirname(__FILE__).'/../data/testdrive.db',
		),*/
		// uncomment the following to use a MySQL database
		'db'=>array(
			'connectionString' => 'mysql:host=SILVA;dbname=14indt8db',
			'emulatePrepare' => true,
			'username' => '14indt8',
			'password' => '111.acc',
			'charset' => 'utf8',
		),
		'errorHandler'=>array(
			// use 'site/error' action to display errors
			'errorAction'=>'site/error',
		),
		'log'=>array(
			'class'=>'CLogRouter',
			'routes'=>array(
				array(
					'class'=>'CFileLogRoute',
					'levels'=>'error, warning',
				),
				// uncomment the following to show log messages on web pages
				/*
				array(
					'class'=>'CWebLogRoute',
				),
				*/
			),
		),

		'bootstrap' => array(
            'class' => 'bootstrap.components.TbApi',   
        ),
	),

	// application-level parameters that can be accessed
	// using Yii::app()->params['paramName']
	'params'=>array(
		'projectPath' => 'C:/websites/2014-projects/team8/web/',
		'siteDomain' => 'https://zeno.computing.dundee.ac.uk/2014-projects/team8/web/',
		'imagePath' => 'images/u/',
		'videoPath' => 'videos/u/',
		// this is used in contact page
		'adminEmail'=>'webmaster@example.com',
	),
);