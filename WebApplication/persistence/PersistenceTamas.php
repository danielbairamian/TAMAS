<?php


class PersistenceTamas {
	private $filename;

	function __construct($filename = 'ecse321data.txt') {
		
		$this->filename = $filename;
	}

	function loadDataFromStore() {
		if (file_exists($this->filename)) {
			$str = file_get_contents($this->filename);
			$department = unserialize($str);
		} else {
			$department = new Department();
		}

		return $department;
	}
	function writeDataToStore($department) {
		$str = serialize($department);
		file_put_contents($this->filename, $str);
	}
}
?>