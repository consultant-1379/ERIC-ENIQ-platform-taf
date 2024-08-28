shipment=`cat /eniq/admin/version/eniq_status | grep Shipment |cut -d " " -f2 | cut -d '_' -f4-`;
release=`echo $shipment | cut -d"." -f1`;
num=`echo $shipment | cut -d"." -f2`;
numToAlpha=( ['1']='A.1' ['2']='B' ['3']='B.1' ['4']='C');
featurePath=`echo "Features_$release${numToAlpha[$num]}_$shipment"`;
echo $featurePath