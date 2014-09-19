<?php

/**
 * This is the model class for table "potato_Tutorial".
 *
 * The followings are the available columns in table 'potato_Tutorial':
 * @property integer $Id
 * @property string $Name
 * @property string $Description
 * @property string $VideoName
 */
class Tutorial extends CActiveRecord
{
	public $video;		// Used for uploading a file.

	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Tutorial the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'potato_Tutorial';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('Name, Description', 'required'),
			array('Name', 'length', 'max'=>50),
			array('VideoName', 'length', 'max'=>37),
			array('video', 'file','types'=>'mp4', 'allowEmpty'=>true),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('Id, Name, Description, VideoName', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
		);
	}

	public function beforeSave()
	{
		if(isset($this->video))
		{
			// Check whether a video exist.
			if(isset($this->VideoName))
			{
				// Check whether this file exist
				$path = Yii::app()->params['projectPath'].Yii::app()->params['videoPath'].$this->VideoName;

				if(file_exists($path))
				{
					// Delete the old file..
					unlink($path);
				}
			}

			// Generate name for the new video.
			do 
			{
				// Generate a name for this image.
				$this->VideoName = md5(time()).'.mp4';
			}while(file_exists(Yii::app()->params['projectPath'].Yii::app()->params['videoPath'].$this->VideoName));

			// Save the video.
			move_uploaded_file($this->video->getTempName(),Yii::app()->params['projectPath'].Yii::app()->params['videoPath'].$this->VideoName);
		}

		return true;
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'Id' => 'ID',
			'Name' => 'Name',
			'Description' => 'Description',
			'VideoName' => 'Video Name',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		// Warning: Please modify the following code to remove attributes that
		// should not be searched.

		$criteria=new CDbCriteria;

		$criteria->compare('Id',$this->Id);
		$criteria->compare('Name',$this->Name,true);
		$criteria->compare('Description',$this->Description,true);
		$criteria->compare('VideoName',$this->VideoName,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}