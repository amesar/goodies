
# Sample script that calls JsonValidator agains a valid instance.

datadir=testdata

schema=$datadir/basic/schema-basic.json
instance=$datadir/basic/invalid/multiple-errors.json

validate.sh $schema $instance | tee oo
