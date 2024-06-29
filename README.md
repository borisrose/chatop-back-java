# Procédure 

## Docker 

J'ai mis un petit compose.yaml concernant la base de données.

## Architecture 

J'ai accentué la modulation de l'architecture ( notamment dans le dossier security )

## Utiliser l'application

Vous lancez banckend en utilisant vscode directement si vous avez les binaires nécessaires sur votre machine. En effet, je n'ai pas fait de service dans le compose.yaml qui aurait pu faire cela automatiquement pour vous. Donc il faut pour utiliser ce backend avoir un moyen de compiler le java sur votre système. 

Une fois cela fait l'application va tourner sur le port 3001 

## Utiliser le frontend 

Une fois le serveur lancé, vous pouvez démarrer l'application frontend qui va consommer l'api.

## Le Swagger 

Lien vers le swagger (http://localhost:3001/swagger-ui.html)
PS: http://localhost:3001/swagger-ui.html