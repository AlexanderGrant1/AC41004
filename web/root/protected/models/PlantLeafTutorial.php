<?php

/**
 * This is the model class for table "potato_PlantLeaf_tutorial".
 *
 * The followings are the available columns in table 'potato_PlantLeaf_tutorial':
 * @property integer $Id
 * @property integer $PlantLeafId
 * @property integer $TutorialId
 *
 * The followings are the available model relations:
 * @property PotatoPlantLeaf $plantLeaf
 * @property PotatoTutorial $tutorial
 */
class PlantLeafTutorial extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return PlantLeafTutorial the static model class
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
		return 'potato_PlantLeaf_tutorial';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('PlantLeafId, TutorialId', 'required'),
			array('PlantLeafId, TutorialId', 'numerical', 'integerOnly'=>true),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('Id, PlantLeafId, TutorialId', 'safe', 'on'=>'search'),
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
			'plantLeaf' => array(self::BELONGS_TO, 'PotatoPlantLeaf', 'PlantLeafId'),
			'tutorial' => array(self::BELONGS_TO, 'PotatoTutorial', 'TutorialId'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'Id' => 'ID',
			'PlantLeafId' => 'Plant Leaf',
			'TutorialId' => 'Tutorial',
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
		$criteria->compare('PlantLeafId',$this->PlantLeafId);
		$criteria->compare('TutorialId',$this->TutorialId);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}