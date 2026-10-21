param(
    [Parameter(Mandatory = $true)]
    [ValidateSet('swing', 'socket-server', 'socket-client', 'rmi-server', 'rmi-client', 'serialization', 'i18n-en', 'i18n-hi', 'url', 'jndi')]
    [string]$Demo
)

$ErrorActionPreference = 'Stop'
$classes = Join-Path $PSScriptRoot '..\target\classes'
if (-not (Test-Path -LiteralPath $classes)) {
    throw 'Build first with: mvn clean package'
}
$java = (Get-Command java -ErrorAction Stop).Source
$mainClass = switch ($Demo) {
    'swing' { 'com.shopsphere.swing.AdminDemo' }
    'socket-server' { 'com.shopsphere.network.InventoryServer' }
    'socket-client' { 'com.shopsphere.network.InventoryClient' }
    'rmi-server' { 'com.shopsphere.rmi.InventoryServerMain' }
    'rmi-client' { 'com.shopsphere.rmi.InventoryClientMain' }
    'serialization' { 'com.shopsphere.demo.SerializationDemo' }
    'i18n-en' { 'com.shopsphere.demo.I18nDemo' }
    'i18n-hi' { 'com.shopsphere.demo.I18nDemo' }
    'url' { 'com.shopsphere.demo.UrlConnectionDemo' }
    'jndi' { 'com.shopsphere.demo.JndiDemo' }
}
$arguments = @('-cp', $classes, $mainClass)
if ($Demo -eq 'i18n-hi') { $arguments += 'hi' }
& $java @arguments
exit $LASTEXITCODE
