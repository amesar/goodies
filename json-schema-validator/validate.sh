
# Validates JSON Schema instance

. ./common.env

if [ $# -lt 2 ] ; then
  echo "ERROR: expecting JSON schema and instance schema file"
  exit 1
  fi

schema=$1
instance=$2
  
PGM=com.amm.jsonschema.validator.JsonValidatorDriver

java $PROPS -classpath $CPATH $PGM $schema $instance | tee log.txt

